import java.util.*;
public class WordTree {
	
	private Node root;
	
	public WordTree() {
		root = new Node();
	}
	
	//Accepts a String and adds that word to the wordTree.
	//Returns true if successfully added, false if the word is already in the tree
	public boolean add(String word) {
		if(word.length() == 0) return false;
		return add(word.substring(1), word.charAt(0), root);
	}
	private boolean add(String word, char letter, Node top) {
		Node n = top.getChild(letter);
		if(n != null) {
			if(word.length() == 0) return false;
			return add(word.substring(1), word.charAt(0), n);
		}
		else {
			if(word.length() == 0) {
				if(top.getChild(letter) != null) {
					return false;
				} else {
					top.addChild(letter, true);
					return true;
				}
			} else {
				return add(word.substring(1), word.charAt(0), top.addChild(letter, false));
			}
		}
	}
	
	//Accepts a String and removes that word from the WordTree.
	//Returns true if successfully removed, false if the tree does not contain the word
	public boolean remove(String word) {
		if(!contains(word)) return false;
    
		Stack<Node> stk = new Stack<>();
		Node n = root.getChild(word.charAt(0));
		stk.push(n);
	
		for(int i = 1; i < word.length(); i++) {
			n = n.getChild(word.charAt(i));
			stk.push(n);
		}
    
		if(stk.peek().children.size() > 0) {
			stk.peek().endOfWord = false;
			return true;
		} 
		int index = word.length() - 1;
		stk.pop();
    if(!stk.isEmpty()){
		stk.peek().children.remove(stk.peek().getChild(word.charAt(index)));
		index--;
		while(!stk.isEmpty()) {
			n = stk.pop();
			if(n.endOfWord) return true;
			if(n.children.size() > 1) {
				n.children.remove(n.getChild(word.charAt(index + 1)));
				return true;
			}
			n.children.remove(n.getChild(word.charAt(index)));
			index--;
		}
    }
		return true;
	}
	
	//Accepts a String, returns true if the wordTree contains that word, false if it does not.
	public boolean contains(String word) {
		return contains(word.substring(1), word.charAt(0), root);
	}
	private boolean contains(String word, char letter, Node top) {
		Node next = top.getChild(letter);
		if(word.length() == 0) {
			if(next != null) {
				if(next.endOfWord) return true;
				else return false;
			} else return false;
		} if(next != null) return contains(word.substring(1), word.charAt(0), next);
		else return false;
	}
	
	//Removes all words from the wordTree
	public void clear() {
		root.children.clear();
	}
	
	//Returns a String of all the nodes in the wordTree
	public String toString() {
		return root + "";
	}
	
	//Counts the number of nodes in the tree and returns that number as an int
	public int nodeCount() {
		return nodeCount(root) - 1;
	}
	private int nodeCount(Node top) {
		int count = 1;
		for(int i = 0; i < top.children.size(); i++) {
			count += nodeCount(top.children.get(i));
		}
		return count;
	}
	
	//Counts the number of unique words in the tree and returns that number as an int
	public int wordCount() {
		return wordCount(root);
	}
	private int wordCount(Node top) {
		int count = 0;
		for(int i = 0; i < top.children.size(); i++) {
			if(top.children.get(i).endOfWord) count += 1 + wordCount(top.children.get(i));
			else count += wordCount(top.children.get(i));
		}
		return count;
	}
	
	//Counts the number of letters in all words in the tree and returns the difference between that and the number of nodes
	public int lettersSaved() {
		int count = 0;
		Set<String> words = allWords();
		for(String s : words) {
			count += s.length();
		}
		return count - nodeCount();
	}
	
	//Returns a set with all of the unique words in the wordTree
	public Set<String> allWords() {
		Set<String> words = new HashSet<String>();
		allWords(words, root, "");
		return words;
	}
	private void allWords(Set<String> words, Node top, String word) {
    if(top != null)
		for(Node n : top.children) {
			String tempWord = word + n.letter;
			if(n.endOfWord) {
				words.add(tempWord);
			}
			allWords(words, n, tempWord);
		}
	}
	
	//Returns a set of all of the words in the tree which start with a specific string
	public Set<String> allStartingWith(String s) {
		Set<String> words = new HashSet<String>();
		Node n = nodeStartingWith(s, root);
		allWords(words, n, s);
		return words;
	}
	private Node nodeStartingWith(String s, Node top) {
		if(s.length() > 0 && top != null) {
			return nodeStartingWith(s.substring(1), top.getChild(s.charAt(0)));
		}
		return top;
	}
	
	//Returns a Map<Character, Set<String>> where the key is the first letter of each word, 
	//and the value is a set of all words in the tree which start with that letter
	public Map<Character, Set<String>> wordMap() {
		Map<Character, Set<String>> wordMap = new HashMap<Character, Set<String>>();
		for(Node n : root.children) {
			wordMap.put(n.letter, allStartingWith(n.letter + ""));
		}
		return wordMap;
	}
	
	private class Node {
		private char letter;
		private List<Node> children;
		private boolean endOfWord;
		public Node() {
			this.children = new ArrayList<Node>();
			this.endOfWord = false;
		}
		
		public Node(char letter, boolean endOfWord) {
			this.letter = letter;
			this.children = new ArrayList<Node>();
			this.endOfWord = endOfWord;
		}
		
		public String toString() {
			return (endOfWord ? (letter + "").toUpperCase() : letter) + " " + children;
		}
		public Node addChild(char letter, boolean end) {
			Node n = new Node(letter, end);
			children.add(n);
			return n;
		}
		public Node getChild(char letter) {
			for(int i = 0; i < children.size(); i++) {
				if(children.get(i).letter == letter) {
					return children.get(i);
				}
			}
			return null;
		}
	}
}
