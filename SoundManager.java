import javax.sound.sampled.*;


public class SoundManager {

    
    public static void play(String soundName) {
        new Thread(() -> {
            try {
                int sampleRate = 22050; 
                byte[] buffer;

                switch (soundName) {
                    case "click": {
                        
                        double duration = 0.05; 
                        int numSamples = (int) (sampleRate * duration);
                        buffer = new byte[numSamples];
                        for (int i = 0; i < numSamples; i++) {
                            double t = (double) i / sampleRate;
                            
                            double progress = (double) i / numSamples;
                            double freq = 750.0 - (570.0 * progress);
                            double angle = 2.0 * Math.PI * freq * t;
                            double wave = Math.sin(angle);
                            
                            double envelope = Math.pow(1.0 - progress, 2);
                            buffer[i] = (byte) (wave * envelope * 100.0);
                        }

                        break;
                    }
                    case "start": {
                        
                        double noteDuration = 0.08; 
                        int noteSamples = (int) (sampleRate * noteDuration);
                        double[] freqs = {523.25, 659.25, 783.99, 1046.50}; 
                        buffer = new byte[noteSamples * freqs.length];
                        
                        for (int n = 0; n < freqs.length; n++) {
                            double freq = freqs[n];
                            int offset = n * noteSamples;
                            for (int i = 0; i < noteSamples; i++) {

                                double t = (double) i / sampleRate;
                                double angle = 2.0 * Math.PI * freq * t;
                                
                                double triangle = 2.0 * Math.abs(2.0 * (t * freq - Math.floor(t * freq + 0.5))) - 1.0;
                                double wave = 0.6 * triangle + 0.4 * Math.sin(angle);
                                double envelope = 1.0 - ((double) i / noteSamples);
                                buffer[offset + i] = (byte) (wave * envelope * 60.0);
                            }
                        }
                        break;
                    }

                    case "win": {
                        
                        double noteDuration = 0.12;
                        int noteSamples = (int) (sampleRate * noteDuration);

                        double[] arpeggio = {523.25, 659.25, 783.99, 1046.50}; 

                        double sustainDuration = 0.6;
                        int sustainSamples = (int) (sampleRate * sustainDuration);
                        int totalSamples = noteSamples * arpeggio.length + sustainSamples;

                        buffer = new byte[totalSamples];

                    
                        for (int n = 0; n < arpeggio.length; n++) {
                            double freq = arpeggio[n];
                            int offset = n * noteSamples;
                            for (int i = 0; i < noteSamples; i++) {

                                double t = (double) i / sampleRate;
                                double angle = 2.0 * Math.PI * freq * t;
                                double wave = Math.sin(angle);

                                double envelope = 1.0 - (0.4 * (double) i / noteSamples);

                                buffer[offset + i] = (byte) (wave * envelope * 70.0);
                            }
                        }

                        
                        int sustainOffset = noteSamples * arpeggio.length;
                        for (int i = 0; i < sustainSamples; i++) {
                            double t = (double) i / sampleRate;
                            double w1 = Math.sin(2.0 * Math.PI * 523.25 * t);
                            double w2 = Math.sin(2.0 * Math.PI * 659.25 * t);
                            double w3 = Math.sin(2.0 * Math.PI * 783.99 * t);
                            double w4 = Math.sin(2.0 * Math.PI * 1046.50 * t);
                            
                            double wave = (w1 + w2 + w3 + w4) / 4.0;
                            
                            double envelope = 1.0 - ((double) i / sustainSamples);
                            buffer[sustainOffset + i] = (byte) (wave * envelope * 80.0);

                        }
                        break;
                    }
                    case "draw": {
                        
                        double duration = 0.4;

                        int numSamples = (int) (sampleRate * duration);
                        buffer = new byte[numSamples];

                        for (int i = 0; i < numSamples; i++) {

                            double t = (double) i / sampleRate;

                            double progress = (double) i / numSamples;
                            
                            double f1 = 392.00 - (100.0 * progress); 
                            double f2 = 396.00 - (100.0 * progress); 
                            double w1 = Math.sin(2.0 * Math.PI * f1 * t);
                            double w2 = Math.sin(2.0 * Math.PI * f2 * t);
                            double wave = (w1 + w2) / 2.0;
                            double envelope = Math.cos(progress * Math.PI / 2.0); 
                            buffer[i] = (byte) (wave * envelope * 75.0);
                        }
                        break;
                    }
                    default:

                        return;

                }

                
                AudioFormat format = new AudioFormat(sampleRate, 8, 1, true, true);
                SourceDataLine line = AudioSystem.getSourceDataLine(format);
                line.open(format);
                line.start();
                line.write(buffer, 0, buffer.length);
                line.drain();
                line.close();
            } catch (Exception e) {
                
            }

        }).start();
        
    }

    public static void click() { play("click"); }
    public static void win()   { play("win"); }
    public static void draw()  { play("draw"); }
    public static void start() { play("start"); }
}