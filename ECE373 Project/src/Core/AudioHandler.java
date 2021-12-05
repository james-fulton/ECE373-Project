package Core;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.ArrayList;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioHandler {
	private ArrayList<ArrayList> audioClips;
	private ArrayList<ArrayList> audioLengths;
	private final float MASTER_GAIN = (float)0.8;
	
	private final float PLAYER_FOOTSTEP_GAIN = MASTER_GAIN * (float)0.2;
	private final float PLAYER_RELOAD_GAIN = MASTER_GAIN * (float)0.4;
	private final float PLAYER_NOAMMO_GAIN = MASTER_GAIN * (float)0.6;
	private final float PLAYER_SHOOT_GAIN = MASTER_GAIN * (float)0.8;
	
	private final float ZOMBIE_FOOTSTEP_GAIN = MASTER_GAIN *(float)0.25;
	private final float ZOMBIE_AMBIENT_GAIN = MASTER_GAIN *(float)0.3;
	private final float ZOMBIE_TAUNT_GAIN = MASTER_GAIN * (float)0.6;
	private final float ZOMBIE_SPRINT_GAIN = MASTER_GAIN * (float)0.5;
	private final float ZOMBIE_DEATH_GAIN = MASTER_GAIN * (float)0.5;
	
	public AudioHandler() {
		audioClips = new ArrayList<ArrayList>();
		audioLengths = new ArrayList<ArrayList>();
		loadAudio();
	}
	
	public ArrayList<ArrayList> getAudioClips(){
		return audioClips;
	}
	public Clip getAudioClip(int indexA, int indexB){
		ArrayList<Clip> clips = audioClips.get(indexA);
		return clips.get(indexB);
	}
	public float getAudioLength(int indexA, int indexB){
		ArrayList<Float> lengths = audioLengths.get(indexA);
		return lengths.get(indexB);
	}
	public ArrayList<ArrayList> getAudioLengths(){
		return audioLengths;
	}
	
	public void setVolume(float volume, Clip clip) {
	    if (volume < 0f || volume > 1f)
	        throw new IllegalArgumentException("Volume not valid: " + volume);
	    FloatControl gain = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);   
	    gain.setValue(20f * (float) Math.log10(volume));
	}
	
	
	private void loadAudio() {
		//Order:
		
		//Ambient noises: 1x
		//Player footsteps: 6x
		//Zombie noises: 89x
		//Weapons: 32x
		
		//Gib Sounds:
		//Interaction Sounds:
		//Power-up Sounds:
		//Round Start & Stop: 1x
		//Music: TBT
		
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		//---------------------------------Ambient---------------------------------
		//
		//Compromises of: (in order) 1 Sounds
		//1x air raid sirens
		
		ArrayList<Clip> ambientSound = new ArrayList<Clip>();
		ArrayList<Float> ambientLength = new ArrayList<Float>();
		//(1) air_raid_loop_03_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/air_raid/air_raid_loop_03_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			ambientLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume((float)0.3, clip1);
			ambientSound.add(clip1);
			
			//play
			clip1.setFramePosition(0);
            clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load air_raid_loop_03_fix.wav");
			System.out.println(ex);
		}
		audioClips.add(ambientSound);
		audioLengths.add(ambientLength);
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		//-------------------------Player Footsteps--------------------------------
		//
		//Compromises of: (in order) 6 Sounds
		//6x types of grass run
		//6x types of grass walk
		
		ArrayList<Clip> playerFootstepSound = new ArrayList<Clip>();
		ArrayList<Float> playerFootstepLength = new ArrayList<Float>();
		
		//(1) grass_run_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/footsteps/grass_run/grass_run_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			playerFootstepLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			playerFootstepSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load grass_run_00_fix.wav");
			System.out.println(ex);
		}
		
		//(2) grass_run_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/footsteps/grass_run/grass_run_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			playerFootstepLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			playerFootstepSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load grass_run_01_fix.wav");
			System.out.println(ex);
		}
		
		//(3) grass_run_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/footsteps/grass_run/grass_run_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			playerFootstepLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			playerFootstepSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load grass_run_02_fix.wav");
			System.out.println(ex);
		}
		
		//(4) grass_run_03_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/footsteps/grass_run/grass_run_03_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			playerFootstepLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			playerFootstepSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load grass_run_03_fix.wav");
			System.out.println(ex);
		}
		
		//(5) grass_run_04_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/footsteps/grass_run/grass_run_04_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			playerFootstepLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			playerFootstepSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load grass_run_04_fix.wav");
			System.out.println(ex);
		}
		
		//(6) grass_run_05_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/footsteps/grass_run/grass_run_05_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			playerFootstepLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			playerFootstepSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load grass_run_05_fix.wav");
			System.out.println(ex);
		}
		
		//(1) grass_walk_00_fix.wav
				try {
					//file search
					Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
					File file = new File(path + "/sounds/footsteps/grass_walk/grass_walk_00_fix.wav");
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
					
					//length
					AudioFormat format = audioInputStream.getFormat();
					long audioFileLength = file.length();
					int frameSize = format.getFrameSize();
					float frameRate = format.getFrameRate();
					float durationInSeconds = (audioFileLength / (frameSize * frameRate));
					playerFootstepLength.add(durationInSeconds);
					
					//load
					Clip clip1 = AudioSystem.getClip();
					clip1.open(audioInputStream);
					setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
					playerFootstepSound.add(clip1);
					
					//play
					//clip1.setFramePosition(0);
		            //clip1.loop(-1);
				}
				catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
					System.out.println("ERROR: Unable to load grass_walk_00_fix.wav");
					System.out.println(ex);
				}
				
				//(2) grass_walk_01_fix.wav
				try {
					//file search
					Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
					File file = new File(path + "/sounds/footsteps/grass_walk/grass_walk_01_fix.wav");
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
					
					//length
					AudioFormat format = audioInputStream.getFormat();
					long audioFileLength = file.length();
					int frameSize = format.getFrameSize();
					float frameRate = format.getFrameRate();
					float durationInSeconds = (audioFileLength / (frameSize * frameRate));
					playerFootstepLength.add(durationInSeconds);
					
					//load
					Clip clip1 = AudioSystem.getClip();
					clip1.open(audioInputStream);
					setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
					playerFootstepSound.add(clip1);
					
					//play
					//clip1.setFramePosition(0);
		            //clip1.loop(-1);
				}
				catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
					System.out.println("ERROR: Unable to load grass_walk_01_fix.wav");
					System.out.println(ex);
				}
				
				//(3) grass_walk_02_fix.wav
				try {
					//file search
					Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
					File file = new File(path + "/sounds/footsteps/grass_walk/grass_walk_02_fix.wav");
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
					
					//length
					AudioFormat format = audioInputStream.getFormat();
					long audioFileLength = file.length();
					int frameSize = format.getFrameSize();
					float frameRate = format.getFrameRate();
					float durationInSeconds = (audioFileLength / (frameSize * frameRate));
					playerFootstepLength.add(durationInSeconds);
					
					//load
					Clip clip1 = AudioSystem.getClip();
					clip1.open(audioInputStream);
					setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
					playerFootstepSound.add(clip1);
					
					//play
					//clip1.setFramePosition(0);
		            //clip1.loop(-1);
				}
				catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
					System.out.println("ERROR: Unable to load grass_walk_02_fix.wav");
					System.out.println(ex);
				}
				
				//(4) grass_walk_03_fix.wav
				try {
					//file search
					Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
					File file = new File(path + "/sounds/footsteps/grass_walk/grass_walk_03_fix.wav");
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
					
					//length
					AudioFormat format = audioInputStream.getFormat();
					long audioFileLength = file.length();
					int frameSize = format.getFrameSize();
					float frameRate = format.getFrameRate();
					float durationInSeconds = (audioFileLength / (frameSize * frameRate));
					playerFootstepLength.add(durationInSeconds);
					
					//load
					Clip clip1 = AudioSystem.getClip();
					clip1.open(audioInputStream);
					setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
					playerFootstepSound.add(clip1);
					
					//play
					//clip1.setFramePosition(0);
		            //clip1.loop(-1);
				}
				catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
					System.out.println("ERROR: Unable to load grass_walk_03_fix.wav");
					System.out.println(ex);
				}
				
				//(5) grass_walk_04_fix.wav
				try {
					//file search
					Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
					File file = new File(path + "/sounds/footsteps/grass_walk/grass_walk_04_fix.wav");
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
					
					//length
					AudioFormat format = audioInputStream.getFormat();
					long audioFileLength = file.length();
					int frameSize = format.getFrameSize();
					float frameRate = format.getFrameRate();
					float durationInSeconds = (audioFileLength / (frameSize * frameRate));
					playerFootstepLength.add(durationInSeconds);
					
					//load
					Clip clip1 = AudioSystem.getClip();
					clip1.open(audioInputStream);
					setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
					playerFootstepSound.add(clip1);
					
					//play
					//clip1.setFramePosition(0);
		            //clip1.loop(-1);
				}
				catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
					System.out.println("ERROR: Unable to load grass_walk_04_fix.wav");
					System.out.println(ex);
				}
				
				//(6) grass_walk_05_fix.wav
				try {
					//file search
					Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
					File file = new File(path + "/sounds/footsteps/grass_walk/grass_walk_05_fix.wav");
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
					
					//length
					AudioFormat format = audioInputStream.getFormat();
					long audioFileLength = file.length();
					int frameSize = format.getFrameSize();
					float frameRate = format.getFrameRate();
					float durationInSeconds = (audioFileLength / (frameSize * frameRate));
					playerFootstepLength.add(durationInSeconds);
					
					//load
					Clip clip1 = AudioSystem.getClip();
					clip1.open(audioInputStream);
					setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
					playerFootstepSound.add(clip1);
					
					//play
					//clip1.setFramePosition(0);
		            //clip1.loop(-1);
				}
				catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
					System.out.println("ERROR: Unable to load grass_walk_05_fix.wav");
					System.out.println(ex);
				}
		
		audioClips.add(playerFootstepSound);
		audioLengths.add(playerFootstepLength);
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		//-------------------------Zombie Sounds-----------------------------------
		//
		//Compromises of: (in order) 89 Sounds
		//
		//4x walking noises
		//3x 'whoosh' or physical attack noises
		//21x ambient noises (walking)
		//15x sprint #1 noises (scarier)
		              //9x sprint #2 noises (not as scary as #1) NOTE: WILL NOT BE USED... yet.
		//5x 'behind' noises (uncommonly used)
		//7x taunt noises (replaces ambient noises when player is close)
		//23x attack noises
		//11x death noises
		
		ArrayList<Clip> zombieSound = new ArrayList<Clip>();
		ArrayList<Float> zombieLength = new ArrayList<Float>();
		
		//(1) steps_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/steps/steps_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load steps_00_fix.wav");
			System.out.println(ex);
		}
		
		//(2) steps_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/steps/steps_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load steps_01_fix.wav");
			System.out.println(ex);
		}
		
		//(3) steps_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/steps/steps_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load steps_02_fix.wav");
			System.out.println(ex);
		}
		
		//(4) steps_03_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/steps/steps_03_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load steps_03_fix.wav");
			System.out.println(ex);
		}
		
		//(1) whoosh_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/whoosh/whoosh_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load whoosh_00_fix.wav");
			System.out.println(ex);
		}
		
		//(2) whoosh_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/whoosh/whoosh_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load whoosh_01_fix.wav");
			System.out.println(ex);
		}
		
		//(3) whoosh_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/whoosh/whoosh_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load whoosh_02_fix.wav");
			System.out.println(ex);
		}
		
		//(1) ambient_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_00_fix.wav");
			System.out.println(ex);
		}
		
		//(2) ambient_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_01_fix.wav");
			System.out.println(ex);
		}
		
		//(3) ambient_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_02_fix.wav");
			System.out.println(ex);
		}
		
		//(4) ambient_03_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_03_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_03_fix.wav");
			System.out.println(ex);
		}
		
		//(5) ambient_04_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_04_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_04_fix.wav");
			System.out.println(ex);
		}
		
		//(6) ambient_05_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_05_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_05_fix.wav");
			System.out.println(ex);
		}
		
		//(7) ambient_06_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_06_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_06_fix.wav");
			System.out.println(ex);
		}
		
		//(8) ambient_07_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_07_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_07_fix.wav");
			System.out.println(ex);
		}
		
		//(9) ambient_08_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_08_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_08_fix.wav");
			System.out.println(ex);
		}
		
		//(10) ambient_09_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_09_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_09_fix.wav");
			System.out.println(ex);
		}
		
		//(11) ambient_10_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_10_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_10_fix.wav");
			System.out.println(ex);
		}
		
		//(12) ambient_11_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_11_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_11_fix.wav");
			System.out.println(ex);
		}
		
		//(13) ambient_12_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_12_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_12_fix.wav");
			System.out.println(ex);
		}
		
		//(14) ambient_13_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_13_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_13_fix.wav");
			System.out.println(ex);
		}
		
		//(15) ambient_14_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_14_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_14_fix.wav");
			System.out.println(ex);
		}
		
		//(16) ambient_15_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_15_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_15_fix.wav");
			System.out.println(ex);
		}
		
		//(17) ambient_16_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_16_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_16_fix.wav");
			System.out.println(ex);
		}
		
		//(18) ambient_17_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_17_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_17_fix.wav");
			System.out.println(ex);
		}
		
		//(19) ambient_18_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_18_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_18_fix.wav");
			System.out.println(ex);
		}
		
		//(20) ambient_19_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_19_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_19_fix.wav");
			System.out.println(ex);
		}
		
		//(21) ambient_20_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/ambient/ambient_20_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_AMBIENT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load ambient_20_fix.wav");
			System.out.println(ex);
		}
		
		//(1) sprint_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_00_fix.wav");
			System.out.println(ex);
		}
		
		//(2) sprint_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_01_fix.wav");
			System.out.println(ex);
		}
		
		//(3) sprint_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_02_fix.wav");
			System.out.println(ex);
		}
		
		//(4) sprint_03_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_03_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_03_fix.wav");
			System.out.println(ex);
		}
		
		//(5) sprint_04_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_04_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_04_fix.wav");
			System.out.println(ex);
		}
		
		//(6) sprint_05_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_05_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_05_fix.wav");
			System.out.println(ex);
		}
		
		//(7) sprint_06_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_06_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_06_fix.wav");
			System.out.println(ex);
		}
		
		//(8) sprint_07_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_07_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_07_fix.wav");
			System.out.println(ex);
		}
		
		//(9) sprint_08_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_08_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_08_fix.wav");
			System.out.println(ex);
		}
		
		//(10) sprint_09_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_09_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_09_fix.wav");
			System.out.println(ex);
		}
		
		//(11) sprint_10_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_10_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_10_fix.wav");
			System.out.println(ex);
		}
		
		//(12) sprint_11_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_11_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_11_fix.wav");
			System.out.println(ex);
		}
		
		//(13) sprint_12_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_12_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_12_fix.wav");
			System.out.println(ex);
		}
		
		//(14) sprint_13_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_13_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_13_fix.wav");
			System.out.println(ex);
		}
		
		//(15) sprint_14_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/sprint/sprint_14_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_SPRINT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load sprint_14_fix.wav");
			System.out.println(ex);
		}
		
		//(1) behind_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/behind/behind_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load behind_00_fix.wav");
			System.out.println(ex);
		}
		
		//(2) behind_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/behind/behind_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load behind_01_fix.wav");
			System.out.println(ex);
		}
		
		//(3) behind_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/behind/behind_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load behind_02_fix.wav");
			System.out.println(ex);
		}
		
		//(4) behind_03_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/behind/behind_03_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load behind_03_fix.wav");
			System.out.println(ex);
		}
		
		//(5) behind_04_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/behind/behind_04_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load behind_04_fix.wav");
			System.out.println(ex);
		}
		
		//(5) behind_04_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/behind/behind_04_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load behind_04_fix.wav");
			System.out.println(ex);
		}
		
		//(1) taunt_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/taunt/taunt_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_TAUNT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load taunt_00_fix.wav");
			System.out.println(ex);
		}
		
		//(2) taunt_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/taunt/taunt_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_TAUNT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load taunt_01_fix.wav");
			System.out.println(ex);
		}
		
		//(3) taunt_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/taunt/taunt_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_TAUNT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load taunt_02_fix.wav");
			System.out.println(ex);
		}
		
		//(4) taunt_03_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/taunt/taunt_03_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_TAUNT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load taunt_03_fix.wav");
			System.out.println(ex);
		}
		
		//(5) taunt_04_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/taunt/taunt_04_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_TAUNT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load taunt_04_fix.wav");
			System.out.println(ex);
		}
		
		//(6) taunt_05_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/taunt/taunt_05_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_TAUNT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load taunt_05_fix.wav");
			System.out.println(ex);
		}
		
		//(7) taunt_06_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/taunt/taunt_06_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_TAUNT_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load taunt_06_fix.wav");
			System.out.println(ex);
		}
		
		//(1) attack_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_00_fix.wav");
			System.out.println(ex);
		}
		
		//(2) attack_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_01_fix.wav");
			System.out.println(ex);
		}
		
		//(3) attack_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_02_fix.wav");
			System.out.println(ex);
		}
		
		//(4) attack_03_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_03_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_03_fix.wav");
			System.out.println(ex);
		}
		
		//(5) attack_04_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_04_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_04_fix.wav");
			System.out.println(ex);
		}
		
		//(6) attack_05_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_05_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_05_fix.wav");
			System.out.println(ex);
		}
		
		//(7) attack_06_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_06_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_06_fix.wav");
			System.out.println(ex);
		}
		
		//(8) attack_07_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_07_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_07_fix.wav");
			System.out.println(ex);
		}
		
		//(9) attack_08_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_08_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_08_fix.wav");
			System.out.println(ex);
		}
		
		//(10) attack_09_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_09_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_09_fix.wav");
			System.out.println(ex);
		}
		
		//(11) attack_10_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_10_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_10_fix.wav");
			System.out.println(ex);
		}
		
		//(12) attack_11_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_11_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_11_fix.wav");
			System.out.println(ex);
		}
		
		//(13) attack_12_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_12_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_12_fix.wav");
			System.out.println(ex);
		}
		
		//(14) attack_13_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_13_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_13_fix.wav");
			System.out.println(ex);
		}
		
		//(15) attack_14_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_14_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_14_fix.wav");
			System.out.println(ex);
		}
		
		//(16) attack_15_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_15_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_15_fix.wav");
			System.out.println(ex);
		}
		
		//(17) attack_16_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_16_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_16_fix.wav");
			System.out.println(ex);
		}
		
		//(18) attack_17_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_17_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_17_fix.wav");
			System.out.println(ex);
		}
		
		//(19) attack_18_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_18_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_18_fix.wav");
			System.out.println(ex);
		}
		
		//(20) attack_19_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_19_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_19_fix.wav");
			System.out.println(ex);
		}
		
		//(21) attack_20_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_20_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_20_fix.wav");
			System.out.println(ex);
		}
		
		//(22) attack_21_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_21_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_21_fix.wav");
			System.out.println(ex);
		}
		
		//(23) attack_22_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/attack/attack_22_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_FOOTSTEP_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load attack_22_fix.wav");
			System.out.println(ex);
		}
		
		//(1) death_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_00_fix.wav");
			System.out.println(ex);
		}
		
		//(2) death_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_01_fix.wav");
			System.out.println(ex);
		}
		
		//(3) death_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_02_fix.wav");
			System.out.println(ex);
		}
		
		//(4) death_03_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_03_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_03_fix.wav");
			System.out.println(ex);
		}
		
		//(5) death_04_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_04_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_04_fix.wav");
			System.out.println(ex);
		}
		
		//(6) death_05_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_05_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_05_fix.wav");
			System.out.println(ex);
		}
		
		//(7) death_06_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_06_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_06_fix.wav");
			System.out.println(ex);
		}
		
		//(8) death_07_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_07_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_07_fix.wav");
			System.out.println(ex);
		}
		
		//(9) death_08_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_08_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_08_fix.wav");
			System.out.println(ex);
		}
		
		//(10) death_09_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_09_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_09_fix.wav");
			System.out.println(ex);
		}
		
		//(11) death_10_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/zombie/zombie_vox/death/death_10_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			zombieLength.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(ZOMBIE_DEATH_GAIN, clip1);
			zombieSound.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load death_10_fix.wav");
			System.out.println(ex);
		}

		audioClips.add(zombieSound);
		audioLengths.add(zombieLength);
		
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		//-------------------------Weapon Sounds-----------------------------------
		//
		//Compromises of: (in order) 32 Sounds
		//
		// Two reload sounds per gun (full then half)
		//
		//Bowie Knife: 3x stab & 3x swing (6x) -> 0 to 5
		//Empty gun: 4x -> 6 to 9
		//M1911: 1x fire & 2x reload (3x) -> 10 to 12
		//M1Garand: 1x fire & 2x reload (3x) -> 13 to 15
		//Thompson: 1x fire & 2x reload (3x) -> 16 to 18
		//DoubleBarrelShotgun: 1x fire & 2x reload (3x) -> 19 to 21
		//K98k: 1x fire & 2x reload (3x) -> 22 to 24
		//MG42: 1x fire & 2x reload (3x) -> 25 to 27
		//ShellCasings: 4x (30-06 & 8mm mauser, shotgun, 45ACP, 9Luger) -> 28 to 31
		//Weapon Pickup: 3x -> 32 to 34
		
		//additional guns added later:
		//MP40: 1x fire & 2x reload -> 35 to 37
		
		ArrayList<Clip> weaponSounds = new ArrayList<Clip>();
		ArrayList<Float> weaponLengths = new ArrayList<Float>();
		
		//(1) bowie_stab_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/bowie/bowie_stab/bowie_stab_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume((float)0.5, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load bowie_stab_00_fix.wav");
			System.out.println(ex);
		}
		
		//(2) bowie_stab_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/bowie/bowie_stab/bowie_stab_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume((float)0.5, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load bowie_stab_01_fix.wav");
			System.out.println(ex);
		}
		
		//(3) bowie_stab_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/bowie/bowie_stab/bowie_stab_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume((float)0.5, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load bowie_stab_02_fix.wav");
			System.out.println(ex);
		}
		
		//(4) bowie_swing_00_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/bowie/bowie_swing/bowie_swing_00_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume((float)0.5, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load bowie_swing_00_fix.wav");
			System.out.println(ex);
		}
		
		//(5) bowie_swing_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/bowie/bowie_swing/bowie_swing_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume((float)0.5, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load bowie_swing_01_fix.wav");
			System.out.println(ex);
		}
		
		//(6) bowie_swing_02_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/bowie/bowie_swing/bowie_swing_02_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume((float)0.5, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load bowie_swing_02_fix.wav");
			System.out.println(ex);
		}
		
		//(1) pistol_dry_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/empty_weapon/pistol_dry_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_NOAMMO_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load pistol_dry_fix.wav");
			System.out.println(ex);
		}
		
		//(2) submachinegun_dry_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/empty_weapon/submachinegun_dry_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_NOAMMO_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load submachinegun_dry_fix.wav");
			System.out.println(ex);
		}
		
		//(3) shotgun_dry_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/empty_weapon/shotgun_dry_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_NOAMMO_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load shotgun_dry_fix.wav");
			System.out.println(ex);
		}
		
		//(4) rifle_dry_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/empty_weapon/rifle_dry_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_NOAMMO_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load rifle_dry_fix.wav");
			System.out.println(ex);
		}
		
		
		//(1) M1911 fire_01_fix.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/colt1911/fire/fire_01_fix.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load fire_01_fix.wav");
			System.out.println(ex);
		}
		
		//(2) M1911 m1911reload_full.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/colt1911/reload/m1911reload_full.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_RELOAD_GAIN * (float)1.4, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load m1911reload_full.wav");
			System.out.println(ex);
		}
		
		//(3) M1911 m1911reload_half.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/colt1911/reload/m1911reload_half.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_RELOAD_GAIN * (float)1.4, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load m1911reload_half.wav");
			System.out.println(ex);
		}
		
		//(1) M1 Garand m1garand_fire.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/m1garand/m1garand_fire.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN * (float)1.5, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load m1garand_fire.wav");
			System.out.println(ex);
		}
		
		//(2) M1 Garand m1garand_full_reload.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/m1garand/m1garand_full_reload.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load m1garand_full_reload.wav");
			System.out.println(ex);
		}
		
		//(3) M1 Garand m1garand_half_reload.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/m1garand/m1garand_half_reload.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load m1garand_half_reload.wav");
			System.out.println(ex);
		}
		
		//(1) Thompson thompson_fire.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/thompson/thompson_fire.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load thompson_fire.wav");
			System.out.println(ex);
		}
		
		//(2) Thompson thompson_full_reload.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/thompson/thompson_full_reload.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load thompson_full_reload.wav");
			System.out.println(ex);
		}
		
		//(3) Thompson thompson_half_reload.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/thompson/thompson_half_reload.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load thompson_half_reload.wav");
			System.out.println(ex);
		}
		
		//(1) Double Barrel double_barrel_fire.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/double_barrel/double_barrel_fire.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load double_barrel_fire.wav");
			System.out.println(ex);
		}
		
		//(2) Double Barrel double_barrel_full_reload.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/double_barrel/double_barrel_full_reload.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load double_barrel_full_reload.wav");
			System.out.println(ex);
		}
		
		//(3) Double Barrel double_barrel_half_reload.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/double_barrel/double_barrel_half_reload.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load double_barrel_half_reload.wav");
			System.out.println(ex);
		}
		
		//(1) k98k k98_fire.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/k98k/k98_fire.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN * (float)0.7, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load k98_fire.wav");
			System.out.println(ex);
		}
		
		//(2) k98k k98_reload.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/k98k/k98_reload.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load k98_reload.wav");
			System.out.println(ex);
		}
		
		//(3) k98k k98_rechamber.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/k98k/k98_rechamber.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load k98_rechamber.wav");
			System.out.println(ex);
		}
		
		//(1) MG42 mg42_fire.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/mg42/mg42_fire.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load mg42_fire.wav");
			System.out.println(ex);
		}
		
		//(2) MG42 mg42_full_reload.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/mg42/mg42_full_reload.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load mg42_full_reload.wav");
			System.out.println(ex);
		}
		
		//(3) MG42 mg42_half_reload.wav
		try {
			//file search
			Path path = FileSystems.getDefault().getPath("").toAbsolutePath();
			File file = new File(path + "/sounds/weapons/mg42/mg42_half_reload.wav");
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
			
			//length
			AudioFormat format = audioInputStream.getFormat();
			long audioFileLength = file.length();
			int frameSize = format.getFrameSize();
			float frameRate = format.getFrameRate();
			float durationInSeconds = (audioFileLength / (frameSize * frameRate));
			weaponLengths.add(durationInSeconds);
			
			//load
			Clip clip1 = AudioSystem.getClip();
			clip1.open(audioInputStream);
			setVolume(PLAYER_SHOOT_GAIN, clip1);
			weaponSounds.add(clip1);
			
			//play
			//clip1.setFramePosition(0);
            //clip1.loop(-1);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
			System.out.println("ERROR: Unable to load mg42_half_reload.wav");
			System.out.println(ex);
		}
		
		
		audioClips.add(weaponSounds);
		audioLengths.add(weaponLengths);

		
	}
}
