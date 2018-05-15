package javabean;

public class Score {
	private float score;

	public float getScore() {
		return score;
	}

	public void setScore(float score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "Score [score=" + score + "]";
	}

}
