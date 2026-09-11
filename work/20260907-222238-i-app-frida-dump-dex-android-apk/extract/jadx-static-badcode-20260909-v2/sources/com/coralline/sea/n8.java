package com.coralline.sea;

import java.util.LinkedList;
import java.util.Observable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: assets/RiskStub.dex */
public class n8 extends Observable {
    public static final Integer e = 1;
    public static n8 f;
    public LinkedList<s1> a = new LinkedList<>();
    public ReentrantLock b;
    public Condition c;
    public u4 d;

    public n8() {
        ReentrantLock reentrantLock = new ReentrantLock();
        this.b = reentrantLock;
        this.c = reentrantLock.newCondition();
        this.d = h9.b().c();
    }

    public static synchronized n8 c() {
        if (f == null) {
            f = new n8();
        }
        return f;
    }

    public s1 a() {
        this.b.lock();
        while (this.a.isEmpty()) {
            try {
                this.c.await();
            } catch (Exception e2) {
                this.b.unlock();
                return null;
            } catch (Throwable th) {
                this.b.unlock();
                throw th;
            }
        }
        s1 s1VarRemove = this.a.remove(0);
        if (this.a.isEmpty()) {
            setChanged();
            notifyObservers(e);
        }
        this.b.unlock();
        return s1VarRemove;
    }

    public boolean a(s1 s1Var) {
        this.b.lock();
        try {
            this.a.add(s1Var);
            this.c.signalAll();
            this.b.unlock();
            return true;
        } catch (Throwable th) {
            this.b.unlock();
            throw th;
        }
    }

    public boolean b() {
        this.b.lock();
        try {
            return this.a.isEmpty();
        } finally {
            this.b.unlock();
        }
    }

    public boolean b(s1 s1Var) {
        if (z1.r) {
            this.d.a(s1Var);
            return true;
        }
        a(s1Var);
        return true;
    }
}
