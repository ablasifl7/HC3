package ricoh.es.presentation.utils;

import org.openqa.selenium.WebElement;

public class Check {
	private WebElement element;
	private boolean error;
	private String msgError;
	public WebElement getElement() {
		return element;
	}
	public boolean isError() {
		return error;
	}
	public String getMsgError() {
		return msgError;
	}
	public Check(WebElement element, boolean error, String msgError) {
		super();
		this.element = element;
		this.error = error;
		this.msgError = msgError;
	}
	
}
