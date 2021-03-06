package uk.co.thomaspickup.spacewars.gage.engine;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import uk.co.thomaspickup.spacewars.gage.engine.audio.Music;
import uk.co.thomaspickup.spacewars.gage.engine.audio.Sound;
import uk.co.thomaspickup.spacewars.gage.engine.io.FileIO;

/**
 * Asset store for holding loaded assets.
 *
 * @version 1.0
 */
public class AssetStore {

    // /////////////////////////////////////////////////////////////////////////
    // Properties
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Bitmap asset store
     */
    private HashMap<String, Bitmap> mBitmaps;

    /**
     * Music asset store
     */
    private HashMap<String, Music> mMusic;

    /**
     * Sound asset store
     */
    private HashMap<String, Sound> mSounds;
    private SoundPool mSoundPool;

    /**
     * File IO
     */
    private FileIO mFileIO;

    // /////////////////////////////////////////////////////////////////////////
    // Constructors
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Create a new asset store
     *
     * @param fileIO Context to which this File IO will use
     */
    public AssetStore(FileIO fileIO) {
        mFileIO = fileIO;
        mBitmaps = new HashMap<String, Bitmap>();
        mMusic = new HashMap<String, Music>();
        mSounds = new HashMap<String, Sound>();
        mSoundPool = new SoundPool(Sound.MAX_CONCURRENT_SOUNDS,
                AudioManager.STREAM_MUSIC, 0);
    }

    // /////////////////////////////////////////////////////////////////////////
    // Store //
    // /////////////////////////////////////////////////////////////////////////

    /**
     * Add the specified bitmap asset to the store
     *
     * @param assetName Name given to the asset
     * @param asset     Bitmap asset to add
     * @return boolean true if the asset could be added, false it not (e.g. an
     * asset with the specified name already exists).
     */
    public boolean add(String assetName, Bitmap asset) {
        if (mBitmaps.containsKey(assetName))
            return false;

        mBitmaps.put(assetName, asset);
        return true;
    }

    /**
     * Add the specified music asset to the store
     *
     * @param assetName Name given to the asset
     * @param asset     Music asset to add
     * @return boolean true if the asset could be added, false it not (e.g. an
     * asset with the specified name already exists).
     */
    public boolean add(String assetName, Music asset) {
        if (mBitmaps.containsKey(assetName))
            return false;

        mMusic.put(assetName, asset);
        return true;
    }

    /**
     * Add the specified sound asset to the store
     *
     * @param assetName Name given to the asset
     * @param asset     Sound asset to add
     * @return boolean true if the asset could be added, false it not (e.g. an
     * asset with the specified name already exists).
     */
    public boolean add(String assetName, Sound asset) {
        if (mSounds.containsKey(assetName))
            return false;

        mSounds.put(assetName, asset);
        return true;
    }

    /**
     * Load and add the specified bitmap asset to the store
     *
     * @param assetName  Name given to the asset
     * @param bitmapFile Location of the bitmap asset
     * @return boolean true if the asset could be loaded and added, false if not
     */
    public boolean loadAndAddBitmap(String assetName, String bitmapFile) {

        boolean success = true;
        try {
            Bitmap bitmap = mFileIO.loadBitmap(bitmapFile, null);
            success = add(assetName, bitmap);
        } catch (IOException e) {
            Log.e("Gage", "AssetStore.loadAndAddBitmap: Cannot load ["
                    + bitmapFile + "]");
            success = false;
        }

        return success;
    }

    /**
     * Load and add the specified music asset to the store
     *
     * @param assetName Name given to the asset
     * @param musicFile Location of the music asset
     * @return boolean true if the asset could be loaded and added, false if not
     */
    public boolean loadAndAddMusic(String assetName, String musicFile) {
        boolean success = true;
        try {
            Music music = mFileIO.loadMusic(musicFile);
            success = add(assetName, music);

        } catch (IOException e) {
            Log.e("Gage", "AssetStore.loadAndAddMusic: Cannot load ["
                    + musicFile + "]");
            success = false;
        }

        return success;
    }

    /**
     * Load and add the specified sound asset to the store
     *
     * @param assetName Name given to the asset
     * @param soundFile Location of the sound asset
     * @return boolean true if the asset could be loaded and added, false if not
     */
    public boolean loadAndAddSound(String assetName, String soundFile) {
        boolean success = true;
        try {
            Sound sound = mFileIO.loadSound(soundFile, mSoundPool);
            success = add(assetName, sound);
        } catch (IOException e) {
            Log.e("Gage", "AssetStore.loadAndAddSound: Cannot load ["
                    + soundFile + "]");
            success = false;
        }

        return success;
    }

    /**
     * Retrieve the specified bitmap asset from the store
     *
     * @param assetName Name of the asset to retrieve
     * @return Bitmap asset, null if the named asset could not be found
     */
    public Bitmap getBitmap(String assetName) {
        return mBitmaps.get(assetName);
    }

    /**
     * Retrieve the specified music asset from the store
     *
     * @param assetName Name of the asset to retrieve
     * @return Music asset, null if the named asset could not be found
     */
    public Music getMusic(String assetName) {
        return mMusic.get(assetName);
    }

    /**
     * Retrieve the specified sound asset from the store
     *
     * @param assetName Name of the asset to retrieve
     * @return Sound asset, null if the named asset could not be found
     */
    public Sound getSound(String assetName) {
        return mSounds.get(assetName);
    }

    /**
     * Retrives the specified text file
     *
     * @param context passes through the app context
     * @param filename passes through the file name of the text file
     * @return the String content of the text file
     * @throws IOException
     */
    public static String getTextFile(Context context, String filename) throws IOException {
        // Creates a new buffered reader to loop through the text file
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(context.getAssets().open(filename)));

        // Creates an output to return
        String output = "";

        // Reads the first line
        String currentLine = bufferedReader.readLine();

        // Loops through the text file until there is no more to loop through
        while (currentLine != null) {
            // Appends the current line to output
            output += currentLine;

            // Adds a new carrige return after every line
            output += System.getProperty("line.separator");

            // Reads the next line
            currentLine = bufferedReader.readLine();
        }

        // Disposes of the reader
        bufferedReader.close();

        // Returns the output via the function
        return output;
    }
}