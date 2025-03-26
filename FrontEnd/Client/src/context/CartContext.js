import React, { createContext, useState, useContext, useEffect } from 'react';

const CartContext = createContext();

export const useCart = () => useContext(CartContext);

export const CartProvider = ({ children }) => {
  const [cart, setCart] = useState(() => {
    const savedCart = localStorage.getItem('cart');
    return savedCart ? JSON.parse(savedCart) : [];
  });

  const [totalAmount, setTotalAmount] = useState(() => {
    return cart.reduce((acc, item) => acc + item.price * item.quantity, 0);
  });

  const [discount, setDiscount] = useState(() => {
    const savedDiscount = localStorage.getItem('discount');
    return savedDiscount ? JSON.parse(savedDiscount) : 0;
  });

  const [discountedTotal, setDiscountedTotal] = useState(0);

  const applyDiscount = (discountValue) => {
    setDiscount(discountValue);
    localStorage.setItem('discount', JSON.stringify(discountValue));
  };

  const removeDiscount = () => {
    setDiscount(0);
    localStorage.removeItem('discount');
  };

  useEffect(() => {
    localStorage.setItem('cart', JSON.stringify(cart));
    const newTotalAmount = cart.reduce((acc, item) => acc + item.price * item.quantity, 0);
    const discountedAmount = newTotalAmount - discount;
    setTotalAmount(newTotalAmount);
    setDiscountedTotal(discountedAmount > 0 ? discountedAmount : 0);
    localStorage.setItem('totalAmount', discountedAmount > 0 ? discountedAmount : 0);
    
    console.log('Cart updated:', cart);
    console.log('New total amount:', newTotalAmount);
    console.log('Discounted amount:', discountedAmount);
  }, [cart, discount]);

  const addToCart = (product, quantity) => {
    setCart((prevCart) => {
      const existingItem = prevCart.find(item => item.id === product.id);
      if (existingItem) {
        // Update quantity if the item already exists
        return prevCart.map(item =>
          item.id === product.id ? { ...item, quantity: item.quantity + quantity } : item
        );
      }
      // Add new item if it doesn't exist
      return [...prevCart, { ...product, quantity }];
    });
  };

  const removeFromCart = (id) => {
    setCart((prevCart) => prevCart.filter(item => item.id !== id));
  };

  const updateQuantity = (id, quantity) => {
    setCart((prevCart) =>
      prevCart.map(item =>
        item.id === id ? { ...item, quantity } : item
      )
    );
  };

  const clearCart = () => {
    setCart([]);
  };

  return (
    <CartContext.Provider value={{ cart, addToCart, removeFromCart, updateQuantity, clearCart, totalAmount, discountedTotal, applyDiscount, removeDiscount, discount }}>
      {children}
    </CartContext.Provider>
  );
};