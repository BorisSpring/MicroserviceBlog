import React, { useState } from 'react';
import { LoadingSpinner } from '../..';

import { useGeAllCategories } from './useGetAllCategories';
import { useUpdateCategory } from './useUpdateCategory';
import { useDeleteCategory } from './useDeleteCategory';
import { useCreateCategory } from './useCreateCategory';
import { useUpdateCategoryOrder } from './useUpdateCategoryOrder';

const CategoriesList = () => {
  const [categoryState, setCategoryState] = useState({
    isUpdate: false,
    activeCategory: '',
    isAddCategory: false,
    categoryName: '',
  });
  const { deleteCategory, isDeleting } = useDeleteCategory();
  const { createCategory, isCreating } = useCreateCategory();
  const { updateCategory, isUpdateing } = useUpdateCategory();
  const { allCategories, isLoading } = useGeAllCategories();
  const { updateCategoryOrder, isUpdatingOrder } = useUpdateCategoryOrder();

  const handleSubmit = (e) => {
    e.preventDefault();
    if (categoryState.categoryName.length > 4) {
      createCategory(categoryState.categoryName);
      setCategoryState((prev) => ({
        ...prev,
        categoryName: '',
        isAddCategory: false,
      }));
    }
  };

  if (isLoading) <LoadingSpinner />;

  return (
    <div className='h-screen overflow-y-auto'>
      <div className='flex flex-col justify-center md:flex-row md:justify-between items-center bg-primary'>
        <h1 className=' text-white text-[20px] lg:text-[25px] text-center ml-2'>
          Categories List
        </h1>
        <div
          className={` ${
            !categoryState.isAddCategory ? 'scale-100' : 'scale-0'
          } flex w-full md:w-fit justify-between my-5 transition-all duration-300 items-center mr-2`}
        >
          <button
            onClick={() =>
              setCategoryState((prev) => ({
                ...prev,
                isAddCategory: !prev.isAddCategory,
              }))
            }
            className='bg-gray-600 hover:bg-gray-700 text-white h-7 text-[15px] m-auto md:m-0 md:h-8  md:text-[20px] flex items-center justify-center
             transition-all duration-300 focus:ring  focus:ring-gray-400 px-2 lg:px-4 py-1   focus:ring-opacity-80 outline-none '
          >
            Add new Category
          </button>
        </div>
      </div>
      <div className='flex items-center justify-center mt-10'>
        {categoryState.isAddCategory && (
          <form onSubmit={(e) => handleSubmit(e)}>
            <div className='w-full md:max-w-200px'>
              <input
                type='text'
                required
                placeholder='Category name must be over 4 chars'
                value={categoryState.categoryName}
                onChange={(e) =>
                  setCategoryState((prev) => ({
                    ...prev,
                    categoryName: e.target.value,
                  }))
                }
                className='outline-none border-b-2 border-b-gray-500 text-[15px] md:text-[18px] text-primary placeholder:text-[12px] '
              />
            </div>
            <button
              disabled={isCreating}
              type='submit'
              className='bg-gray-600 w-full md:max-w-200px hover:bg-gray-700 text-white h-8 mt-2 flex items-center justify-center
             transition-all duration-300 focus:ring  focus:ring-gray-400 px-2 lg:px-4 py-1 shadow-lg  focus:ring-opacity-80 text-[15px] outline-none'
            >
              Add new Category
            </button>
          </form>
        )}
      </div>
      <div className='flex flex-col gap-5  justify-center '>
        {allCategories?.map?.(({ id, name, order, blogsCount }) => (
          <div
            className='flex flex-col md:flex-row border-b p-1 md:p-3 md:justify-between md:items-center'
            key={id}
          >
            <div className='flex flex-col text-primary'>
              <p className=' md:text-[19px]'>
                Id: <span className='font-[500]'>{id}</span>
              </p>
              <p className=' md:text-[19px]'>
                Name: <span className='font-[500]'>{name}</span>
              </p>
              <p className=' md:text-[19px]'>
                Order Number:{' '}
                <span className='font-[500]'>
                  {order ? order : 'Unordered'}
                </span>
              </p>
              <p className=' md:text-[19px]'>
                Blogs counts:
                <span className='font-[500] '>
                  {' '}
                  {blogsCount} blogs in category
                </span>
              </p>
            </div>
            <div>
              <button
                onClick={() => {
                  setCategoryState((prev) => ({
                    ...prev,
                    activeCategory: id,
                    isUpdate: false,
                  }));
                }}
                className={`bg-gray-500 ${
                  categoryState.activeCategory === id
                    ? 'scale-0 hidden '
                    : 'scale-100 '
                }    hover:bg-gray-600 transition-all duration-500 text-white px-2  text-center p-1  flex items-center justify-center h-6 md:h-7 text-[12px] md:text-[17px] border-none focus:ring focus:ring-opacity-80 focus:ring-gray-400  outline-none`}
              >
                Action
              </button>
              <div
                className={`text-white flex gap-1 ${
                  !categoryState.isUpdate && categoryState.activeCategory === id
                    ? 'scale-100'
                    : 'scale-0 h-0 w-0'
                } transition-all duration-300`}
              >
                <button
                  onClick={() => {
                    setCategoryState((prev) => ({ ...prev, isUpdate: 'name' }));
                  }}
                  className={`bg-yellow-500  hover:bg-yellow-600 outline-none transition-all duration-500  h-6 md:h-7 flex items-center justify-center text-[12px]  px-1 md:px-2   focus:ring focus:ring-opacity-80 focus:ring-green-400 border-none`}
                >
                  Update Name
                </button>
                <button
                  onClick={() => {
                    setCategoryState((prev) => ({
                      ...prev,
                      isUpdate: 'order',
                    }));
                  }}
                  className={`bg-blue-500  hover:bg-blue-600 transition-all  outline-none duration-500  h-6 md:h-7 flex items-center justify-center text-[12px]  px-1 md:px-2   focus:ring focus:ring-opacity-80 focus:ring-green-400 border-none`}
                >
                  Update order
                </button>
                <button
                  onClick={() => deleteCategory(categoryState.activeCategory)}
                  disabled={isDeleting}
                  className={`bg-red-500  hover:bg-red-600 transition-all  outline-none duration-500  px-1 md:px-2 h-6 md:h-7 flex items-center justify-center text-[12px]  focus:ring focus:ring-opacity-80 focus:ring-green-400 border-none`}
                >
                  Delete
                </button>
              </div>
              {categoryState.isUpdate &&
                categoryState.activeCategory === id && (
                  <form
                    onSubmit={(e) => {
                      const { isUpdate, categoryName } = categoryState;
                      e.preventDefault();
                      isUpdate === 'name'
                        ? updateCategory({
                            categoryId: id,
                            categoryName,
                          })
                        : updateCategoryOrder({
                            categoryId: id,
                            orderNumber: categoryName,
                          });
                      setCategoryState((prev) => ({
                        ...prev,
                        isUpdate: false,
                        categoryName: '',
                      }));
                    }}
                  >
                    <div className='relative w-fit'>
                      <input
                        type='text'
                        value={categoryState.categoryName}
                        placeholder='Write here..'
                        onChange={(e) =>
                          setCategoryState((prev) => ({
                            ...prev,
                            categoryName: e.target.value,
                          }))
                        }
                        className='outline-none rounded-full  text-gray-600 text-[15px] border-2 h-7 border-gray-500 px-3 max-w-[200px] maxw-[350px]'
                      />
                      <button
                        disabled={isUpdateing || isUpdatingOrder}
                        type='submit'
                        className='bg-gray-600 hover:bg-gray-700 text-white h-7 mt-2 flex items-center justify-center 
                    transition-all duration-300 focus:ring  focus:ring-gray-400 px-2 lg:px-4 py-1 rounded-full  focus:ring-opacity-80 text-[15px] outline-none absolute -top-[2px] right-0'
                      >
                        Update
                      </button>
                    </div>
                  </form>
                )}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default CategoriesList;
