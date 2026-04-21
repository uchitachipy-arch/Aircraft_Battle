package edu.hitsz.mythread;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class onetimeMusic extends MusicThread{
	public onetimeMusic(String thefilename) {
		super(thefilename);
	}
	@Override
	public void run() {
		InputStream stream = new ByteArrayInputStream(samples);
        play(stream);
	}
}
