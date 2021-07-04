public class Controller {

	private View view;

	private Model model;

	Controller() {
		this.model = new Model();
		this.view = new View(this);
	}

	Controller(String title) {
		this.model = new Model();
		this.view = new View(title, this);
	}

	public Model getModel( ) {
		return model;
	}

	public View getView( ) {
		return view;
	}
}
