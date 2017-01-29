package pallettown;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Owner on 25/01/2017.
 */
public class PTCProxy {

    private String ip;
    private int usages = 0;
    public static final int MAX_USES = 3;
    public static final long RESET_TIME = 660000;
    private ArrayList<Long> startTimes = null;


    PTCProxy(String ip){
        this.ip = ip;
    }

    public void StartUsing(){
        startTimes = new ArrayList<>();
        Use();
    }

    public void Use(){
        startTimes.add(usages,System.currentTimeMillis());
        usages++;
    }

    public void UpdateQueue(){
        usages--;
        startTimes.remove(usages);
        startTimes.forEach(aLong -> System.out.println("    start time: " + aLong));
    }

    public boolean Started(){
        return startTimes != null;
    }

    public boolean Usable(){
        System.out.println("checking if proxy is usable");
        long millis = System.currentTimeMillis() - startTimes.get(0);
        String time = String.format("%02d min, %02d sec",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        System.out.println("proxy running time: " + time + ", usages: " + usages);
        return (millis < RESET_TIME && usages < MAX_USES);
    }

    public String IP(){
        return ip;
    }

    public int Usages(){
        return usages;
    }

    public long WaitTime() {
        return Math.max(RESET_TIME - (System.currentTimeMillis() - startTimes.get(0)),0);
    }
}