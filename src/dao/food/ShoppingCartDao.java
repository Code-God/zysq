package dao.food;

import model.bo.food.ShoppingCart;

import org.springframework.stereotype.Repository;

import com.base.EnhancedHibernateDaoSupport;
@Repository("shoppingCartDao")
public class ShoppingCartDao extends EnhancedHibernateDaoSupport<ShoppingCart> {

	@Override
	protected String getEntityName() {
		return ShoppingCart.class.getName();
	}
}
