import axiosClient from './axiosClient';

const productApi = {
    createProduct(data) {
        const url = '/products';
        return axiosClient.post(url, data);
    },
    getDetailProduct(id) {
        const url = '/products/' + id;
        return axiosClient.get(url);
    },
    getListProducts() {
        const url = '/products';
        return axiosClient.get(url);
    },
    getProductsByCategory(id) {
        const url = '/products/category/' + id;
        return axiosClient.get(url);
    },
    updateProduct(id, data) {
        const url = '/products/' + id;
        return axiosClient.put(url, data);
    },
    deleteProduct(id) {
        const url = '/products/' + id;
        return axiosClient.delete(url);
    },
    searchProduct(name) {
        const params = {
            name: name.target.value
        }
        const url = '/products/searchByName';
        return axiosClient.get(url, { params });
    },
    getPurchasedProductsByUser(userId) {
        const url = `/products/user/${userId}/purchased`;
        return axiosClient.get(url);
    },
}

export default productApi; 