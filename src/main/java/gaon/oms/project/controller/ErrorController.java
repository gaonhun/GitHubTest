package gaon.oms.project.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
//에러 컨트롤러
public class ErrorController {

	// 400, 404 등 에러페이지 처리
	@GetMapping("/errorPage")
	public String errPage() throws IOException {
		return "errorPage";
	}
}
