import axios from 'axios';

const BASE_URL = 'http://localhost:9090/';

const blogApi = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

const blogApiAuth = axios.create({
  baseURL: BASE_URL,
  withCredentials: true,
  headers: {
    'Content-Type': 'application/json',
  },
});

blogApiAuth.interceptors.request.use((config) => {
  const token = localStorage.getItem('access_token');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// messages

export async function sendMessage(message) {
  try {
    const res = await blogApi.post(`/messages/api/messages`, message);
    return res.data;
  } catch (error) {
    return error.response.data.message;
  }
}

export async function getAllMessages(params) {
  try {
    const { data } = await blogApiAuth.get('/messages/api/messages', {
      params: params,
    });
    return data;
  } catch (error) {
    return error.response.data.message;
  }
}

export async function markMsgAsRead(msgId) {
  try {
    return await blogApiAuth.put(`/messages/api/messages/readed/${msgId}`);
  } catch (error) {
    return error.response.data.message;
  }
}

export async function markAsUnread(msgId) {
  try {
    return await blogApiAuth.put(`/messages/api/messages/unread/${msgId}`);
  } catch (error) {
    return error.response.data.message;
  }
}

export async function deleteMessage(msgId) {
  try {
    return await blogApiAuth.delete(`/messages/api/messages/${msgId}`);
  } catch (error) {
    return error.response.data.message;
  }
}

// users

export async function loginUser(loginReq) {
  try {
    const data = await blogApi.post(`/auth/login`, loginReq);
    return data.data;
  } catch (error) {
    throw new Error(error.response.data.msg);
  }
}

export async function getLoggedUser() {
  try {
    const res = await blogApi.get(`/auth/logged`);
    return res.data;
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function updatePasswordRequest(updatePasswordRequest) {
  try {
    return await blogApi.put(
      `/api/users/changePassword`,
      updatePasswordRequest
    );
  } catch (error) {
    console.error(error.response.data.msg);
    return error.response.data;
  }
}

export async function deleteUserImage(userId) {
  try {
    const { data } = await blogApi.delete(`/api/users/image/${userId}`);
    return data;
  } catch (error) {
    return error.response.data.message;
  }
}

export async function updateUserImage(image, userId) {
  try {
    const { data } = await blogApi.post(
      `/api/users/updateImage/${userId}`,
      image,
      {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      }
    );
    return data;
  } catch (error) {
    console.error(error);
  }
}

export async function addNewUser(formData) {
  try {
    return await blogApi.post(`/users/${BASE_URL}auth/signup`, formData, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  } catch (error) {
    return error.response.data;
  }
}

export async function findAllUsers(params) {
  try {
    const { data } = await blogApi.get('/api/users', { params: params });
    return data;
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function unbanUser(userId) {
  try {
    return await blogApi.put(`/api/users/unban/${userId}`);
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function banUser(userId) {
  try {
    return await blogApi.put(`/api/users/ban/${userId}`);
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function deleteUser(userId) {
  try {
    return await blogApi.delete(`/api/users/${userId}`);
  } catch (error) {
    return error.response.data.message;
  }
}

// sliders

export async function addHomeSlider(slideRequest) {
  try {
    return await blogApiAuth.post(`/slides/api/slides`, slideRequest, {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    });
  } catch (error) {
    return error.response.data;
  }
}

export async function getAllHomeSliders(params) {
  try {
    const { data } = await blogApiAuth.get(`/slides/api/slides/allSlides`, {
      params: params,
    });
    return data;
  } catch (error) {
    return error.response.data;
  }
}

export async function enableSlide(slideId) {
  try {
    return await blogApiAuth.put(`/slides/api/slides/enableSlide/${slideId}`);
  } catch (error) {
    return error.response.data;
  }
}

export async function disableSlide(slideId) {
  try {
    return await blogApiAuth.put(`/slides/api/slides/disable/${slideId}`);
  } catch (error) {
    return error.response.data;
  }
}

export async function addSlideOrderNumber(slideId, orderNumber) {
  try {
    return await blogApiAuth.put(
      `/slides/api/slides/${slideId}/${orderNumber}`
    );
  } catch (error) {
    return error.response.data;
  }
}

export async function deleteHomeSlide(slideId) {
  try {
    return await blogApiAuth.delete(`/slides/api/slides/${slideId}`);
  } catch (error) {
    return error.response.data;
  }
}

export async function findEnabledSlides() {
  try {
    return await blogApi.get('/slides/api/slides');
  } catch (error) {
    return error.response.data;
  }
}

//categories

export async function createCategory(category) {
  try {
    return await blogApiAuth.post('/blogs/api/categories', category);
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function updateCategory(categoryId, categoryName) {
  try {
    return await blogApiAuth.put(
      `/blogs/api/categories/${categoryId}`,
      categoryName
    );
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function deleteCategoryById(categoryId) {
  try {
    return await blogApiAuth.delete(`/blogs/api/categories/${categoryId}`);
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function getAllCategories() {
  try {
    const { data } = await blogApi.get('/blogs/api/categories');
    return data;
  } catch (error) {
    return error.response.data.message;
  }
}

export async function updateCategoryOrder(categoryId, orderNumber) {
  try {
    return await blogApiAuth.put(
      `/blogs/api/categories/${categoryId}/${orderNumber}`
    );
  } catch (error) {
    return error.response.data.msg;
  }
}

// blogs

export async function createNewBlog(formData) {
  try {
    return await blogApi.post(`/blogs/api/blogs`, formData, {
      headers: {
        ' Content-Type': 'multipart/form-data',
      },
    });
  } catch (error) {
    return error.response.data;
  }
}

export async function getAllBlogs(params) {
  try {
    const { data } = await blogApi.get(`/blogs/api/blogs/allBlogs`, {
      params: params,
    });
    return data;
  } catch (error) {
    return error.response.data.message;
  }
}

export async function deleteBlogById(blogId) {
  try {
    return await blogApi.delete(`/blogs/api/blogs/${blogId}`);
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function makeBlogImportant(blogId) {
  try {
    return await blogApi.put(`/blogs/api/blogs/important/${blogId}`);
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function makeBlogUnImportant(blogId) {
  try {
    return await blogApi.put(`/blogs/api/blogs/unimportant/${blogId}`);
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function disableBlogById(blogId) {
  try {
    return await blogApi.put(`/blogs/api/blogs/disable/${blogId}`);
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function enableBlogById(blogId) {
  try {
    return await blogApi.put(`/blogs/api/blogs/enable/${blogId}`);
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function findBlogById(blogId) {
  try {
    const { data } = await blogApi.get(`/blogs/api/blogs/${blogId}`);
    return data;
  } catch (error) {
    console.error(error.response.data.msg);
    return error.response.data.msg;
  }
}

export async function findNewest() {
  try {
    const { data } = await blogApi.get(`/blogs/api/blogs/newest`);
    return data;
  } catch (error) {
    throw Error(error.response.data.msg);
  }
}

export async function findLastThreeImportnat() {
  try {
    const { data } = await blogApi.get(`/blogs/api/blogs/lastThreeImportant`);
    return data;
  } catch (error) {
    throw new Error(error.response.data.msg);
  }
}

export async function findBlogsForPage(params) {
  console.log(params.toString());
  try {
    const { data } = await blogApi.get(`/blogs/api/blogs`, { params: params });
    return data;
  } catch (error) {
    throw new Error(error.response.data.msg);
  }
}

export async function find3newest(params) {
  try {
    const { data } = await blogApi.get(`/blogs/api/blogs/threeNewest`);
    return data;
  } catch (error) {
    throw new Error(error.response.data.msg);
  }
}

// comments

export async function addBlogComment(blogId, comment) {
  try {
    const { data } = await blogApi.post(
      `/comments/api/comments/${blogId}`,
      comment
    );
    return data;
  } catch (error) {
    throw new Error(error.response.data.msg);
  }
}

export async function findAllComments(params) {
  try {
    const { data } = await blogApiAuth.get(`/comments/api/comments`, {
      params: params,
    });
    return data;
  } catch (error) {
    throw new Error(error.response.data.msg);
  }
}

export async function deleteCommentById(commentId) {
  try {
    return await blogApiAuth.delete(`/comments/api/comments/${commentId}`);
  } catch (error) {
    console.error(error.response.data.msg);
    return error.response.data.msg;
  }
}

export async function disableCommentById(commentId) {
  try {
    return await blogApiAuth.put(`/comments/api/comments/disable/${commentId}`);
  } catch (error) {
    console.error(error.response.data.msg);
    return error.response.data.msg;
  }
}

export async function enableCommentById(commentId) {
  try {
    return await blogApiAuth.put(`/comments/api/comments/enable/${commentId}`);
  } catch (error) {
    console.error(error.response.data.msg);
    return error.response.data.msg;
  }
}

// tags

export async function deleteTagById(tagId) {
  try {
    return await blogApi.delete(`/blogs/api/tags/${tagId}`);
  } catch (error) {
    return error.response.data.message;
  }
}

export async function updateTagById(tagId, name) {
  try {
    return await blogApi.put(`/blogs/api/tags/${tagId}/${name}`);
  } catch (error) {
    return error.response.data.msg;
  }
}

export async function createTag(tagName) {
  try {
    return await blogApi.post(`/blogs/api/tags`, tagName);
  } catch (error) {
    return error.response.data;
  }
}

export async function findAllTags() {
  try {
    const { data } = await blogApi.get('/blogs/api/tags');
    return data;
  } catch (error) {
    return error.response.data.message;
  }
}

export async function findSlideById(slideId) {
  try {
    const { data } = await blogApiAuth.get(`/slides/api/slides/${slideId}`);
    return data;
  } catch (error) {
    return error.response.data.message;
  }
}

export async function getEnabledSlides() {
  try {
    const { data } = await blogApi.get(`/slides/api/slides`);
    return data;
  } catch (error) {
    console.error(error.message);
  }
}

export async function updateInfoApi(updateInfoRequest) {
  try {
    return await blogApi.put('/api/users/updateInfo', updateInfoRequest);
  } catch (error) {
    console.error(error.response.data.msg);
    return error.response.data.msg;
  }
}
