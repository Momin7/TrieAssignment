package tries.impl;
//Got help from Rishav Sharma
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;

import tries.ITrie;

public class Trie implements ITrie
{
	HashMap<Character, Trie>Children;
	boolean isCompleteWord;
	private Trie parent; 
	
	public Trie(){
		Children = new HashMap<Character, Trie>();
        isCompleteWord = false;
	}
	
	
    
    /* (non-Javadoc)
     * @see tries.ITrie#insert(java.lang.String)
     */
    @Override
    public void insert(String word) {
        // TODO Auto-generated method stub
    	if(word.isEmpty()){
    		isCompleteWord=true;
    		return;
    	}
    	char ch= word.charAt(0);
    	if(Children.containsKey(ch)==false){
    		Trie tem = new Trie(); 
    		tem.parent = this;
    		Children.put(ch, tem );
    	}
    	Children.get(ch).insert(word.substring(1));
    	
    }
    
    /* (non-Javadoc)
     * @see tries.ITrie#hasChild(char)
     */
    @Override
    public boolean hasChild(char letter) {
        // TODO Auto-generated method stub
        return Children.containsKey(letter);
    }

    /* (non-Javadoc)
     * @see tries.ITrie#getChild(char)
     */
    @Override
    public ITrie getChild(char letter) {
         //TODO Auto-generated method stub
        if(!hasChild(letter)){
        	return null;
        }
        return Children.get(letter);
    }
    
    /* (non-Javadoc)
     * @see tries.ITrie#getParent()
     */
    @Override
    public ITrie getParent() {
        // TODO Auto-generated method stub
        
    	return parent;
    }

    /* (non-Javadoc)
     * @see tries.ITrie#followPath(java.lang.String)
     */
    @Override
    public ITrie followPath(String path) {
        // TODO Auto-generated method stub
    	if(path.length()==0){
    		return this;
    	} else{
    		char ch = path.charAt(0);
    		if(!Children.containsKey(ch)){
    			return null;
    		}
    		Trie child = Children.get(ch);
    		return child.followPath(path.substring(1));
    	} 
    	
        
    }

    /* (non-Javadoc)
     * @see tries.ITrie#contains(java.lang.String)
     */
    @Override
    public boolean contains(String word) {
        // TODO Auto-generated method stub
        if(word.isEmpty()){
        	return isCompleteWord;
        } 
        char ch= word.charAt(0);
        if(Children.containsKey(ch)==false){
    		return false;
    	}
        return Children.get(ch).contains(word.substring(1));
        
    }

    
    public void Helper(ITrie t, String s, Set<String> results){
    	if(t.Helper2()){
    		results.add(s);
    	}
    	for(char letter = 'a'; letter <= 'z'; letter++) {
   		   if(t.hasChild(letter))  {
   			   ITrie t2=t.getChild(letter);
    		   
			   Helper(t2, s+letter, results);
   		   }
   	 	}
    }
   
    public boolean Helper2(){
    	return isCompleteWord;
    }
    /* (non-Javadoc)
     * @see tries.ITrie#findAllWords()
     */
    @Override
    public Set<String> findAllWords() {
        // TODO Auto-generated method stub
    	Set<String>allWords = new HashSet<String>();
    	for(char ch = 'a'; ch<='z'; ch++){
    		if(Children.containsKey(ch)){
    			ITrie word1 = Children.get(ch);
    			Helper(word1,ch+"",allWords);
    		}
    		
    	}
    	
    	
    	 
        return allWords;
    }
    
    
    
    
    
   
  
    /* (non-Javadoc)
     * @see tries.ITrie#findWordsBeginningWith(java.lang.String)
     */
    @Override
    public Set<String> findWordsBeginningWith(String prefix) {
    	Set<String>allWords = new HashSet<String>();
    	
			
			findWordsBeginningWithHelper(prefix, "", allWords);
	    	  return allWords;
	    }
		 
    	
  	  
        
    
    public void findWordsBeginningWithHelper(String prefix, String path, Set<String> results){
    	if(isCompleteWord == true){
			if(path.length() >= prefix.length()){
				if(path.substring(0, prefix.length()).equals(prefix)){
					results.add(path);
				}
			}
			
		}
		for(Map.Entry<Character, Trie> map:Children.entrySet()){
    		map.getValue().findWordsBeginningWithHelper(prefix, path + map.getKey(), results);
    	}
    		
    }
    
    /* (non-Javadoc)
     * @see tries.ITrie#findWordsEndingWith(java.lang.String)
     */
    @Override
    public Set<String> findWordsEndingWith(String suffix) {
        // TODO Auto-generated method stub
    	Set<String>allWords = new HashSet<String>();
    	
		
		findWordsEndingWithHelper(suffix, "", allWords);
    	  return allWords;
    
    }
    
    
    public void findWordsEndingWithHelper(String suffix, String path, Set<String> results){
    	int init = path.length();
    	int endit = suffix.length();
    	if(isCompleteWord == true){
			if(path.length() >= suffix.length()){
				if(path.substring(init - endit).equals(suffix)){
					results.add(path);
				}
			}
			
		}
		for(Map.Entry<Character, Trie> map:Children.entrySet()){
    		map.getValue().findWordsEndingWithHelper(suffix, path + map.getKey(), results);
    	}
    		
    }
    
    
    
    /* (non-Javadoc)
     * @see tries.ITrie#findWordsContaining(java.lang.String)
     */
    @Override
    public Set<String> findWordsContaining(String patttern) {
        // TODO Auto-generated method stub
    	Set<String> AllWords = new HashSet<String>();
    	findWordsContainingHelper(patttern, "", AllWords);
  	  	return AllWords;
    
    }
    
    private void findWordsContainingHelper(String pattern, String path, Set<String> results){
    	if(isCompleteWord == true){
    		if(path.contains(pattern)) //if my path contains a pattern then add path to results
        			results.add(path);	  		
    	}
    	
    	
    	for(Map.Entry<Character, Trie> set:Children.entrySet()){ //iterating through the set
    		set.getValue().findWordsContainingHelper(pattern, path + set.getKey(), results);
    	}
    }

    /* (non-Javadoc)
     * @see tries.ITrie#findCloseWordsChangedLetters(java.lang.String, int)
     */
    @Override
    public Set<String> findCloseWordsChangedLetters(String words,
        int distance)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see tries.ITrie#findCloseWordsAddedLetters(java.lang.String, int)
     */
    @Override
    public Set<String> findCloseWordsAddedLetters(String words, int distance) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see tries.ITrie#findCloseWordsRemovedLetters(java.lang.String, int)
     */
    @Override
    public Set<String> findCloseWordsRemovedLetters(String words,
        int distance)
    {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see tries.ITrie#findCloseWordsAllChanges(java.lang.String, int)
     */
    @Override
    public Set<String> findCloseWordsAllChanges(String word, int distance) {
        // TODO Auto-generated method stub
        return null;
    }

}
