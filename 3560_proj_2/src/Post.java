
public class Post {
	private String _poster;
	private String _msg;
	
	public Post() {
		_poster = "";
		_msg = "";
	}
	
	public Post(String user, String msg) {
		_poster = user;
		_msg = msg;
	}
	
	public String getPoster() {
		return _poster;
	}
	
	public String getMsg() {
		return _msg;
	}
	
	public void setPoster(String user) {
		_poster = user;
	}
	
	public void setMsg(String msg) {
		_msg = msg;
	}
	
	public void setPost(String user, String msg) {
		_poster = user;
		_msg = msg;
	}
	
	@Override
    public String toString(){
        return _poster + ": " + _msg;
    }
}
