package team.ojt7.recruitment.model.dto;

public class CandidateCountInfo {

	private Long post;
	private Long applied;
	private Long hired;

	public Long getPost() {
		return post == null ? 0 : post;
	}

	public void setPost(Long post) {
		this.post = post;
	}

	public Long getApplied() {
		return applied == null ? 0 : applied;
	}

	public void setApplied(Long applied) {
		this.applied = applied;
	}

	public Long getHired() {
		return hired == null ? 0 : hired;
	}

	public void setHired(Long hired) {
		this.hired = hired;
	}

}
