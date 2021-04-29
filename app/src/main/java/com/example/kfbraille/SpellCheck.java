package com.example.kfbraille;

import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.Vector;

public class SpellCheck
{
	WordFreq[] wf;

	// constructor
	SpellCheck()
	{
	} // all the work done in 'set' and 'get' methods

	public void setDictionary(WordFreq[] wfArg)
	{
		wf = wfArg;
		Arrays.sort(wf, new WordFreqByWord());
	}

	public WordFreq getWordFreq(String wordArg, int freqArg)
	{
		return new WordFreq(wordArg, freqArg);
	}

	/**
	 * getDictionaryLength
	 * 
	 * 
	 */
	public int getDictionaryLength()
	{
		return wf.length;
	}

	/**
	 * wordInDictionary
	 * 
	 * 
	 */
	public boolean wordInDictionary(String word)
	{
		WordFreq dummy = new WordFreq(word, 0); // use freq = 0 (just for the
												// search)
		int n = Arrays.binarySearch(wf, dummy, new WordFreqByWord());
		return n >= 0 ? true : false;
	}

	/**
	 * getCandidates
	 * 
	 * 
	 */
	public String[] getCandidates(String wordArg, int maxArg)
	{
		String word = wordArg; // the word to look up and correct (if necessary)
		int max = maxArg; // the maximum size of the candidate list
		String[] candidates = null; // put the candidates here

		Vector<WordFreq> v1 = new Vector<WordFreq>(); // words with MSD = 1
		Vector<WordFreq> v2 = new Vector<WordFreq>(); // words with MSD = 2
		Vector<WordFreq> v3 = new Vector<WordFreq>(); // words with MSD = 3

		// scan the entire dictionary and build a list of candidate words
		// with MSD = 1, 2, or 3
		for (int i = 0; i < wf.length; ++i)
		{
			// can't be a candidate if size difference is greater than three
			if (Math.abs(wf[i].getWord().length() - word.length()) > 3)
				continue;

			//int distance = (new MSD2(wf[i].getWord(), word)).getMSD();
			int distance = (new MSD(wf[i].getWord(), word)).getMSD();

			if (distance == 1)
				v1.add(wf[i]);
			else if (distance == 2)
				v2.add(wf[i]);
			else if (distance == 3)
				v3.add(wf[i]);
		}

		WordFreq[] wf1 = new WordFreq[v1.size()];
		WordFreq[] wf2 = new WordFreq[v2.size()];
		WordFreq[] wf3 = new WordFreq[v3.size()];

		v1.copyInto(wf1);
		v2.copyInto(wf2);
		v3.copyInto(wf3);

		Arrays.sort(wf1, new WordFreqByFreq());
		Arrays.sort(wf2, new WordFreqByFreq());
		Arrays.sort(wf3, new WordFreqByFreq());

		// debug
		System.out.print("wf1_list: ");
		for (int i = 0; i < wf1.length && i < 10; ++i)
			System.out.print("(" + wf1[i].getWord() + "," + wf1[i].getFreq() + ") ");
		System.out.println();

		System.out.print("wf2_list: ");
		for (int i = 0; i < wf2.length && i < 10; ++i)
			System.out.print("(" + wf2[i].getWord() + "," + wf2[i].getFreq() + ") ");
		System.out.println();

		System.out.print("wf3_list: ");
		for (int i = 0; i < wf3.length & i < 10; ++i)
			System.out.print("(" + wf3[i].getWord() + "," + wf3[i].getFreq() + ") ");
		System.out.println();

		int total = wf1.length + wf2.length + wf3.length;
		int size = total > max ? max : total;

		if (size == 0)
			return null; // no candidates

		candidates = new String[size];

		for (int j = 0; j < candidates.length; ++j)
		{
			for (int k = 0; k < wf1.length && j < candidates.length; ++k)
				candidates[j++] = wf1[k].getWord();
			for (int k = 0; k < wf2.length && j < candidates.length; ++k)
				candidates[j++] = wf2[k].getWord();
			for (int k = 0; k < wf3.length && j < candidates.length; ++k)
				candidates[j++] = wf3[k].getWord();
		}

		// create a new list with candidates at the front that are equal in length to the
		// entered word
		String[] equalLengthCandidatesFirst = new String[candidates.length];
		int countEqual = 0;

		// put the equal-length candidates at the front (in frequency order)
		for (int i = 0; i < candidates.length; ++i)
			if (candidates[i].length() == word.length())
				equalLengthCandidatesFirst[countEqual++] = candidates[i];

		// put the not-equal-length candidates next (in frequency order)
		for (int i = 0; i < candidates.length; ++i)
			if (candidates[i].length() != word.length())
				equalLengthCandidatesFirst[countEqual++] = candidates[i];

		return equalLengthCandidatesFirst;
	}

	public String correctWord(String wordArg)
	{
		String[] temp = getCandidates(wordArg, 10);
		if (!wordInDictionary(wordArg) && temp != null)
			return temp[0];
		else
			return wordArg;
	}

	public String correctPhrase(String phraseArg)
	{
		StringBuffer correctedPhrase = new StringBuffer(100);
		StringTokenizer st = new StringTokenizer(phraseArg);
		while (st.hasMoreTokens())
			correctedPhrase.append(correctWord(st.nextToken()) + " ");
		return correctedPhrase.toString().trim();
	}

	/**
	 * WordFreq - class to hold a word (String) and its frequency (int) from a corpus
	 * 
	 * 
	 */
	public class WordFreq
	{
		String word;
		int freq;

		WordFreq(String wordArg, int freqArg)
		{
			word = wordArg;
			freq = freqArg;
		}

		public String getWord()
		{
			return word;
		}

		public int getFreq()
		{
			return freq;
		}

		public String toString()
		{
			return word + " : " + freq;
		}
	}

	/**
	 * WordFreqByWord -- a class that implements Comparator for sorting WordFreq objects by words.
	 * 
	 */
	public class WordFreqByWord implements Comparator<WordFreq>
	{
		public int compare(WordFreq wf1, WordFreq wf2)
		{
			String s1 = wf1.getWord();
			String s2 = wf2.getWord();
			return s1.compareTo(s2);
		}
	}

	/**
	 * WordFreqByFreq - a class that implements Comparator for sorting WordFreq ojbects by
	 * frequency.
	 * 
	 */
	public class WordFreqByFreq implements Comparator<WordFreq>
	{
		public int compare(WordFreq wf1, WordFreq wf2)
		{
			int i1 = wf1.getFreq();
			int i2 = wf2.getFreq();
			return i2 - i1; // descending
		}
	}
}
