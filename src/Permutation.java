import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.w3c.dom.events.Event;

import com.swabunga.spell.engine.SpellDictionary;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;
import com.swabunga.spell.event.StringWordTokenizer;

public class Permutation implements SpellCheckListener {

	static ArrayList<String> list_of_words = new ArrayList<>();
	
	public Permutation(String letters_a, String word_s) throws FileNotFoundException, IOException
	{
		// TODO Auto-generated method stub
		String letters = letters_a;
		int word_size  = Integer.parseInt(word_s);
		SpellDictionary dictionary = new SpellDictionaryHashMap(new File("eng_com.dic"));
		SpellChecker spellChecker = new SpellChecker(dictionary);
		spellChecker.addSpellCheckListener(this);

		
		permuta(letters, word_size, "", "");
		
		Collections.sort(Permutation.list_of_words);
		
		for (String word : Permutation.list_of_words)
		{
			StringWordTokenizer texTok = new StringWordTokenizer(word);
		    spellChecker.checkSpelling(texTok);
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		new Permutation("prized", "5");
	}
	
	private static void permuta(String l, int size, String comb, String set)
	{
		if (comb.length() == size)
		{
			Permutation.list_of_words.add(comb);			
		}
		else {
			for (Integer i = 0; i < l.length(); i++)
			{
				if (!set.contains(i.toString()))
				{	
					permuta( l, size, comb + l.charAt(i), set + i.toString() );
				}
			}
		}
	}

	@Override
	public void spellingError(SpellCheckEvent event) {
		// TODO Auto-generated method stub
		List sugg = event.getSuggestions();
		if (sugg.size() == 1 && sugg.get(0).toString().equalsIgnoreCase(event.getInvalidWord()))
			System.out.println(event.getInvalidWord() + " >>> " + event.getSuggestions().toString());
	}
}
