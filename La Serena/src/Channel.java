import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SineOscillator;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Channel {
    private final Queue<Note> notes;
    private final ScheduledExecutorService exec;
    private final SineOscillator osc;
    Channel()
    {
        notes = new LinkedList<>();
        exec =  Executors.newScheduledThreadPool(1);
        Synthesizer syn = JSyn.createSynthesizer();
        syn.start();
        osc = new SineOscillator();
        syn.add(osc);
        LineOut line = new LineOut();
        syn.add(line);
        osc.output.connect(0, line.input, 0);
        osc.output.connect(0, line.input, 1);
    }

    public void add(Note note)
    {
        notes.add(note);
    }

    public boolean start(int amp)
    {
        if(!notes.isEmpty())
        {
            osc.amplitude.set(amp);
            Task task = new Task();
            task.run();
            return true;
        }
        System.out.println("Chanel is empty.");
        return false;
    }

    private class Task implements Runnable
    {
        @Override
        public void run() {
            try
            {
                osc.frequency.set(notes.peek().value);
                exec.schedule(this, notes.peek().length, TimeUnit.SECONDS);
                notes.remove();
            }
            catch (NullPointerException e)
            {
                System.out.println("Channel is empty");
            }

        }
    }
}
