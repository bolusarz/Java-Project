package IMP;

import java.io.*;
import java.util.Arrays;

class SecretInput {
    static char[] readInput(InputStream in, String prompt) throws IOException {
        EraserThread eraser = new EraserThread(prompt);
        Thread mask = new Thread(eraser);
        mask.start();
        char[] lineBuffer;
        char[] buf;

        buf = lineBuffer = new  char[128];

        int room = buf.length;
        int offset = 0;
        int c;

       loop: while (true) {
            switch (c = in.read()) {
                case -1:
                case '\n':
                    break loop;
                case '\r':
                    int c2 = in.read();
                    if ((c2 != '\n') && (c2 != -1)) {
                        if (!(in instanceof PushbackInputStream)) {
                            in = new PushbackInputStream(in);
                        }
                        ((PushbackInputStream) in).unread(c2);
                    } else break loop;
                default:
                    if (--room < 0) {
                        buf = new char[offset + 128];
                        room = buf.length - offset - 1;
                        System.arraycopy(lineBuffer, 0 , buf, 0, offset);
                        Arrays.fill(lineBuffer,  ' ');
                        lineBuffer = buf;
                    }
                    buf[offset++] = (char) c;
                    break;
            }
        }
        eraser.stopMasking();
        if (offset == 0) return null;
        char[] ret = new char[offset];
        System.arraycopy(buf, 0, ret, 0, offset);
        Arrays.fill(buf, ' ');
        return ret;
    }
}
