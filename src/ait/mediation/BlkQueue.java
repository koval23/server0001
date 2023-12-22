package ait.mediation;

public interface BlkQueue<T> {
    void push(T message);

    T pop();
}
