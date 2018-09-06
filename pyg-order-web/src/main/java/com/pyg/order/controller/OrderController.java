package com.pyg.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.pyg.cart.service.CartService;
import com.pyg.order.service.OrderService;
import com.pyg.pojo.TbOrder;
import com.pyg.utils.PageResult;
import com.pyg.utils.PygResult;
import com.pyg.vo.Cart;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/order")
public class OrderController {

	@Reference
	private OrderService orderService;

	@Reference
	private CartService cartService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbOrder> findAll(){
		return orderService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult findPage(int page, int rows){
		return orderService.findPage(page, rows);
	}

	
	/**
	 * 修改
	 * @param order
	 * @return
	 */
	@RequestMapping("/update")
	public PygResult update(@RequestBody TbOrder order){
		try {
			orderService.update(order);
			return new PygResult(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new PygResult(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbOrder findOne(Long id){
		return orderService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public PygResult delete(Long [] ids){
		try {
			orderService.delete(ids);
			return new PygResult(true, "删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new PygResult(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbOrder order, int page, int rows  ){
		return orderService.findPage(order, page, rows);		
	}

	@RequestMapping("/findCartList")
	public List<Cart> findCartList(HttpServletRequest request){
		String userName = request.getRemoteUser();
		List<Cart> cartList = cartService.findRedisCartList(userName);
		return cartList;

	}

	@RequestMapping("/submitOrder")
	public PygResult submitOrder(@RequestBody TbOrder tbOrder,HttpServletRequest request){
		try {
			tbOrder.setUserId(request.getRemoteUser());
			//设置订单来源,'2'是pc端
			tbOrder.setSourceType("2");
            List<Cart> cartList = cartService.findRedisCartList(request.getRemoteUser());
            orderService.add(tbOrder,cartList);
			return new PygResult(true,"提交成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new PygResult(false,"提交失败");
		}
	}
	
}
