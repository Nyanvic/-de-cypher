package sample;

import javafx.concurrent.Task;

public class Progress extends Task<String> {
    private double time;

    public Progress(double time) {
        this.time = time;
    }

    @Override
    protected String call() throws InterruptedException {
        int count = 100;
        double iter=0;
        while(iter<=this.time)
        {

            if ((this.time)/100.0<1){
                iter+=0.000005;
                System.out.println("итер "+iter);
                updateProgress(iter, this.time);
                copy(1);
            }
            else{
                iter+=this.time/100;
                System.out.println("итер "+iter);
                updateProgress(iter, this.time);
                copy((this.time)/100.0);
            }

        }
        return "";
    }

    private void copy(double time) throws InterruptedException {
        updateMessage("Операция выполняется");
        System.out.println("ждем "+(long) time);
        Thread.sleep((long) time);

    }
}
