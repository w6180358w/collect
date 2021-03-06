package com.black.web.controller;

import com.black.web.base.bean.BaseModel;
import com.black.web.base.controllers.BaseController;
import com.black.web.base.service.BaseService;
import com.black.web.models.po.Widget;
import com.black.web.models.vo.UserVo;
import com.black.web.services.system.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@RestController
@RequestMapping("/widget")
public class WidgetController extends BaseController<Widget>
{
	@Autowired
	private UserService userService;
	
	@GetMapping("/test")
	@ResponseBody
	public String test() throws Exception {
		this.userService.test1();
        return "aaa";
    }
	
	@GetMapping("/aaa")
	@ResponseBody
	public String aaa() throws Exception {
        return "bbb";
    }
	
	@GetMapping("/load")
	@ResponseBody
	public String load() throws Exception {
        return super.load();
    }

	@GetMapping("/get/{id}")
	@ResponseBody
	public String get(@PathVariable("id") Long id) throws Exception {
        return super.get(id);
    }
	
	@PostMapping("/query")
	@ResponseBody
	public String query(@RequestBody UserVo vo) throws Exception {
        return super.query(vo);
    }
	
	@PostMapping("/select")
	@ResponseBody
	public String select(@RequestBody UserVo vo) throws Exception {
        return super.select(vo);
    }
	
	@PostMapping("/save")
	@ResponseBody
	public String save(@RequestBody Widget widget) throws Exception {
        return super.save(widget);
    }
	
	@PostMapping("/update")
	@ResponseBody
	public String update(@RequestBody Widget widget) throws Exception {
        return super.update(widget);
    }
	
	@PostMapping("/delete")
	@ResponseBody
	public String delete(@RequestBody BaseModel b) throws Exception {
        return super.delete(b);
    }

	@Override
	protected BaseService getService() {
		return userService;
	}

}
