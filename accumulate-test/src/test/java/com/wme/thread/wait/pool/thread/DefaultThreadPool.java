package com.wme.thread.wait.pool.thread;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Title: accumulate-parent
 * @Auther: ming
 * @Date: 2017/11/22
 * @Version: 1.0
 */
public class DefaultThreadPool<Job extends Runnable> implements  ThreadPool<Job> {

    private static final int MAX_WORKER_NUMBERS = 10;
    private static final int DEFAULT_WORKER_NUMBERS = 5;
    private static final int MIN_WORKER_NUMBERS = 1;

    LinkedList<Job> jobs = new LinkedList<>();

    List<Worker> workers = Collections.synchronizedList(new ArrayList<>());

    // 线程编号生成
    private AtomicLong threadNum = new AtomicLong();
    private int workNum = DEFAULT_WORKER_NUMBERS;


    public DefaultThreadPool(){
        initializeWokers(workNum);
    }

    public DefaultThreadPool(int num){
        initializeWokers(num);
    }

    private void initializeWokers (int num) {
        for (int i=0; i<num; i++) {
            Worker worker = new Worker();
            workers.add(worker);
            Thread thread = new Thread(worker, "ThreadPool-Worker-" + threadNum.incrementAndGet());
            thread.start();
        }
    }

    @Override
    public void execute(Job job) {
        if(job != null) {
            synchronized (jobs) {
                // 添加一个工作，然后进行通知
                jobs.addLast(job);
                jobs.notify();
            }
        }
    }

    @Override
    public void shutdown() {
        for (Worker worker : workers) {
            worker.shutdown();
        }
    }

    @Override
    public void addWorkers(int num) {
        synchronized (jobs) {
            if(this.workNum+num > MAX_WORKER_NUMBERS){
                num = MAX_WORKER_NUMBERS - this.workNum;
            }
            if(num>0) {
                initializeWokers(num);
                this.workNum += num;
            }
        }
    }

    @Override
    public void removeWorker(int num) {
        synchronized (jobs) {
            if(this.workNum-num<MIN_WORKER_NUMBERS) {
                num = this.workNum - MIN_WORKER_NUMBERS;
            }
            if(num>0){
                for(int i=0; i<num; i++) {
                    Worker worker = workers.get(i);
                    if(workers.remove(worker)) {
                        worker.shutdown();
                        this.workNum--;
                    }
                }
            }
        }
    }

    @Override
    public int getJobSize() {
        return jobs.size();
    }


    // 工作者，负责消费任务
    class Worker implements Runnable{

        private volatile boolean running = true;

        @Override
        public void run() {
            while (running) {
                Job job = null;
                synchronized (jobs) {
                    if(jobs == null || jobs.isEmpty()){
                        try {
                            jobs.wait();
                        } catch (InterruptedException e) {
                            // 感知到外部对WorkerThread的中断操作，返回 TODO
                            Thread.currentThread().interrupt();
                            return;
                        }
                    }
                    job = jobs.removeFirst();
                }
                if(job != null){
                    try {
                        job.run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void shutdown() {
            running = false;
        }
    }
}
