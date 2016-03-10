public class PageInfo {

	final private String url;
	final private int pageId;
	final private String tfIdf;
	final private String context;

	public PageInfo(String url, int pageId, String tfIdf, String context) {
		this.url = url;
		this.pageId = pageId;
		this.tfIdf = tfIdf;
		this.context = context;
	}

	public String getUrl() {
		return this.url;
	}

	public int getPageId() {
		return this.pageId;
	}

	public String getTfIdf() {
		return this.tfIdf;
	}

	public String getContext() {
		return this.context;
	}
}
