import axiosClient from './axiosClient';

const bookApi = {
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
}

export default bookApi; 