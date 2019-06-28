package com.wfy.zsxq;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.wfy.simple.simpleeventbus.eventbus.SimpleEventBus;
import com.wfy.simple.simpleeventbus.eventbus.ThreadMode;
import com.wfy.simple.simpleeventbus.eventbus.annotations.Subscribe;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity implements UserView {


    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void onMessageEvent(EventMessage event) {
        Log.e("tag", Thread.currentThread().getName() + "" + event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SimpleEventBus.getDefault().unRegister(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        SimpleEventBus.getDefault().register(this);
    }

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mPresenter = new UserPresenter<>();
        mPresenter.mRootView = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LifeCycleActivity.class);
                startActivity(intent);
            }
        });

        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("");
            }
        });
        observable.subscribeOn(Schedulers.io());//ObservableSubscribeOn
        observable.observeOn(Schedulers.io());//ObservableObserveOn
        observable.subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                showLog("onSubscribe");
            }

            @Override
            public void onNext(String s) {
                showLog("onNext");
            }

            @Override
            public void onError(Throwable e) {
                showLog("onError");
            }

            @Override
            public void onComplete() {
                showLog("onComplete");
            }
        });
    }


    private void showLog(String msg) {
        Log.e("tag", msg);
    }

    private void teststr() {
        /**
         * 1、StrongReference：
         * 当强引用对象占用的内存足够多时，JVM就会抛出OutOfMemory
         * 2、SoftReference
         * 只有在内存不足的时候JVM才会回收仅有软引用指向的对象所占的空间。
         * 2、WeakReference
         * 当JVM进行垃圾回收时，无论内存是否充足，都会回收仅被弱引用关联的对象。
         *
         *
         *
         * 结合ReferenceQueue追踪对象是否已经被回收
         *
         *
         * PhantomReference的get()结果必定为null。
         *
         * SoftReference和WeakReference的get()可能为null。
         *
         *
         */

//        new Thread("我要泄漏了") {
//            @Override
//            public void run() {
//                while (true){
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }.start();
//
//
//        new Thread() {
//            @Override
//            public void run() {
//                while (true)
//                    new WeakReference<Person>(new Person("hahah"), queue);
//            }
//        }.start();


        // 首先在堆内存中创建了一个对象，然后在常量池中查找equals "1"的对象，如果没有则创建一个对象保存在常量池中，有的话则不管
//        String s1 = new String("aaa");
//        // 在常量池中
//        String s2 = "aaa";
//       Log.e("tag","1"+ String.valueOf(s1 == s2));   // false
//
//
//        // 首先在堆内存中创建了一个对象，然后在常量池中查找equals "1"的对象，如果没有则创建一个对象保存在常量池中，有的话则不管,之后调用intern()直接返回常量池中的引用
//        s1 = new String("bbb").intern();
//        s2 = "bbb";
//       Log.e("tag","2"+ String.valueOf(s1 == s2));   // true
//
//        //都是返回常量池中的引用
//        s1 = "ccc";
//        s2 = "ccc";
//       Log.e("tag","3"+ String.valueOf(s1 == s2));   // true
//
//
//        //调用intern()直接返回常量池中的引用
//        s1 = new String("ddd").intern();
//        s2 = new String("ddd").intern();
//       Log.e("tag","4"+ String.valueOf(s1 == s2));   // true
//
//
//        // 直接返回常量池中的引用,只要有任何一个不是字符串字面常量形式，都不会在常量池生成"aabb"
//        s1 = "ab" + "cd";
//        s2 = "abcd";
//       Log.e("tag","5"+ String.valueOf(s1 == s2));   // true
//
//
//        //
//        String temp = "hh";
//        s1 = "a" + temp;//只要有任何一个不是字符串字面常量形式，都不会在常量池生成"ahh",且此时jvm做了优化，不会同时生成"a"和"hh"在字符串常量池中
//        // 如果调用s1.intern 则最终返回true
//        s2 = "ahh";//常量池中
//       Log.e("tag","6"+ String.valueOf(s1 == s2));   // false
//
//        temp = "hh";//"hh" 被加入常量池中
//        s1 = "a" + temp;//
//        s2 = "ahh";
//       Log.e("tag","7"+ String.valueOf(s1 == s2));   // false
//
//        temp = "hh";
//        s1 = ("a" + temp).intern();
//        s2 = "ahh";
//       Log.e("tag","8"+ String.valueOf(s1 == s2));   // true
//
//        s1 = new String("1");    // 同时会生成堆中的对象 以及常量池中1的对象，但是此时s1是指向堆中的对象的
//        s1.intern();            // 常量池中的已经存在
//        s2 = "1";
//       Log.e("tag","9"+ String.valueOf(s1 == s2));   // false
//
//        String s3 = new String("1") + new String("1");    // 此时生成了四个对象 常量池中的"1" + 2个堆中的"1" + s3指向的堆中的对象（注此时常量池不会生成"11"）
//        s3.intern();    // jdk1.7之后，常量池不仅仅可以存储对象，还可以存储对象的引用，会直接将s3的地址存储在常量池
//        String s4 = "11";    // jdk1.7之后，常量池中的地址其实就是s3的地址
//        Log.e("tag","10"+ String.valueOf(s3 == s4)); // jdk1.7之前false， jdk1.7之后 true
//
//        s3 = new String("2") + new String("2");
//        s4 = "22";        // 常量池中不存在22，所以会新开辟一个存储22对象的常量池地址
//        s3.intern();    // 常量池22的地址和s3的地址不同
//        Log.e("tag","11"+ String.valueOf(s3 == s4)); // false
        // 对于什么时候会在常量池存储字符串对象，我想我们可以基本得出结论: 1. 显示调用String的intern方法的时候; 2. 直接声明字符串字面常量的时候，例如: String a = "aaa";
        // 3. 字符串直接常量相加的时候，例如: String c = "aa" + "bb";  其中的aa/bb只要有任何一个不是字符串字面常量形式，都不会在常量池生成"aabb". 且此时jvm做了优化，不会同时生成"aa"和"bb"在字符串常量池中
    }

    private void test() {
        //        ViewGroup view = (ViewGroup) findViewById(R.id.root);
//
//
//        HashMap<Object, Object> hashMap = new HashMap<>();
//        hashMap.put("", "");
//
//
//        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 0);
//        atomicStampedReference.compareAndSet(50, 1, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
//
//        startActivity(new Intent());

//        view.post(() -> {
//            float hypot = (float) Math.hypot(view.getHeight(), view.getWidth());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                Animator circularReveal = ViewAnimationUtils.createCircularReveal(view, view.getWidth() / 2,
//                        view.getHeight() / 2, 0, hypot);
//                circularReveal.setDuration(5000);
//                circularReveal.setInterpolator(new AccelerateDecelerateInterpolator());
//                circularReveal.start();
//            }
//        });
//
//        Animation animation = AnimationUtils.loadAnimation(this, R.anim.layoutanim);
//        LayoutAnimationController controller = new LayoutAnimationController(animation);
//        controller.setDelay(0.3f);
//        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
//        view.setLayoutAnimation(controller);
//        view.setLayoutAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//                Log.e("tag", "onAnimationStart");
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                Log.e("tag", "onAnimationEnd");
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//                Log.e("tag", "onAnimationRepeat");
//            }
//        });

//        byte[] proxyClassFile = Proxy.newProxyInstance();
//        Messenger messenger = new Messenger());
//        messenger.send();

//        findViewById(R.id.load).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                OkHttpClient httpClient = new OkHttpClient.Builder()
//                        .build();
//                Call newCall = httpClient.newCall(new Request.Builder().build());
//                try {
//                    newCall.execute().body();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                FragmentManager manager = getSupportFragmentManager();
//                manager.beginTransaction().add(new HolderFragment(), "HolderFragment").commitAllowingStateLoss();
//
//                startActivityForResult(new Intent(MainActivity.this, BActivity.class), 100);

//                new Thread() {
//                    @Override
//                    public void run() {
//                        new MyAsyncTask("MyAsyncTask6").execute("");
//                    }
//                }.start();
//
//                new MyAsyncTask("MyAsyncTask1").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "");
//                new MyAsyncTask("MyAsyncTask2").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                new MyAsyncTask("MyAsyncTask3").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                new MyAsyncTask("MyAsyncTask4").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//                new MyAsyncTask("MyAsyncTask5").executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
//            }
//        });
    }

    private void testFlaMapAndConcat() {
        //实际上concat是串行发送事件的，内部实现是通过同步队列不断for循环取出队列任务
        Observable.concat(Observable.just(1, 2),
                Observable.just(3, 4).observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io()),
                Observable.just(5, 6).observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io()),
                Observable.just(7, 8).observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io()))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("tag", "================concat： " + integer);
                    }
                });


        Observable.merge(Observable.just(1, 2),
                Observable.just(3, 4).observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io()),
                Observable.just(5, 6).observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io()),
                Observable.just(7, 8).observeOn(Schedulers.io())
                        .subscribeOn(Schedulers.io()))
                .doOnLifecycle(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e("tag", "================merge： " + integer);
                    }
                });
    }


    //引用队列
    private ReferenceQueue queue = new ReferenceQueue<Person>();

    /**
     * 监控对象被回收，因为如果被回收就会就如与之关联的队列中
     */
    private void monitorClearedResources() {
        Log.e("tag", "start monitor");
        try {
            int n = 0;
            WeakReference k;
            while ((k = (WeakReference) queue.remove()) != null) {
                Log.e("tag", (++n) + "回收了:" + k + "   object: " + k.get());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("tag", "Nain onActivityResult" + resultCode);
    }
}