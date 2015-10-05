package view;

import java.io.BufferedReader;
import java.io.PrintWriter;

public abstract class MyObservableCLIView extends abstractView {

    protected BufferedReader in;
    protected PrintWriter out;
    protected Thread mainThread;
    
    public MyObservableCLIView(BufferedReader in, PrintWriter out) {
	this.in = in;
	this.out = out;
    }

}
