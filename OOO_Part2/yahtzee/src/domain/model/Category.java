package domain.model;

public enum Category {
	ACES("(1+)"),
	TWOS("(2+)"),
	THREES("(3+)"),
	FOURS("(4+)"),
	FIVES("(5+)"),
	SIXES("(6+)"),
	THREE_OF_A_KIND( "((\\d)\\2\\2)"),
	FOUR_OF_A_KIND("((\\d)\\2\\2\\2)"),
	FULL_HOUSE("((\\d)\\2\\2(\\d)\\3|(\\d)\\4(\\d)\\5\\5)"),
	SMALL_STRAIGHT("(.?1234.?|.?2345.?|.?3456.?)"),
	LARGE_STRAIGHT("(23456|12345)"),
	YAHTZEE("((\\d)\\2\\2\\2\\2)"),
	CHANCE("(.*)");
	
	private final String pattern;
	
	Category(String pattern) {
		this.pattern = pattern;
	}
	
	public String getPattern() {
		return this.pattern;
	}
}
