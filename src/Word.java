	public class Word{
		
		String s;
		int length;
		
		public Word(String s) {
			this.s = s;
			length = s.length();
		}
		
		public String toString() {
			return s + " : " + length;
		}
	}
