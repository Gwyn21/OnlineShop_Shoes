import axiosClient from "./axiosClient";

const API_URL = '/reviews';

const reviewApi = {
  createReview(data) {
    return axiosClient.post(API_URL, data);
  },
  
  getReviewsByProductId(productId) {
    return axiosClient.get(`${API_URL}/product/${productId}`);
  },
  
  deleteReview(id) {
    return axiosClient.delete(`${API_URL}/${id}`);
  }
};

export default reviewApi;
