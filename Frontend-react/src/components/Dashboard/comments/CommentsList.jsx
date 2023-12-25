import React, { useState } from 'react';
import { useNavigate } from 'react-router';
import { useQueryClient } from '@tanstack/react-query';

//components
import { LoadingSpinner, Pagination } from '../..';

// custom hooks
import { useDeleteComment } from './useDeleteComment';
import { useGetParams } from '../../../hooks/useGetParams';
import { findAllComments } from '../../../api/actions';
import { useDisableComment } from './useDisableComment';
import { useEnableComment } from './useEnableComment';
import { useFindAllComments } from './useFindAllComments';
import FilterBox from '../FilterBox';

const filterBy = [
  { navigate: '/dashboard/comments?page=1', label: 'All' },
  { navigate: '/dashboard/comments?page=1&filterBy=enabled', label: 'Enabled' },
  {
    navigate: '/dashboard/comments?page=1&filterBy=disabled',
    label: 'Disabled',
  },
];

const CommentsList = () => {
  const queryClient = useQueryClient();
  const params = useGetParams();
  const currentPage = Number(params.get('page')) || 1;
  const navigate = useNavigate();
  const { enableComment, isEnabling } = useEnableComment();
  const { disableComment, isDisabling } = useDisableComment();
  const { allComments, isLoading } = useFindAllComments();
  const [activeComment, setactiveComment] = useState();
  const { deleteComment, isDeleting } = useDeleteComment(
    allComments?.numberOfElements,
    currentPage
  );

  if (isLoading) return <LoadingSpinner />;

  if (!allComments?.last) {
    params.set('page', currentPage + 1);
    queryClient.prefetchQuery({
      queryFn: () => findAllComments(params),
      queryKey: ['comments', params.toString()],
    });
  }

  return (
    <div className='text-primary'>
      <div className='w-full flex flex-col md:flex-row gap-2 items-center justify-center md:justify-between md:px-5 bg-primary pb-3'>
        <h1 className='text-white '>Comments List </h1>
        <FilterBox filterBy={filterBy} />
        <input
          type='number'
          placeholder='Search by blog id...'
          className='outline-none shadow-md px-2 md:py-1 text-[14px] placeholder:text-primary '
          onKeyDown={(e) => {
            if (e.key === 'Enter') {
              params.set('blogId', e.target.value);
              params.set('page', 1);
              navigate(`?${decodeURIComponent(params.toString())}`);
            }
          }}
        />
      </div>
      {!allComments?.numberOfElements && (
        <h1 className='text-gray-600 text-[24px] w-fit mx-auto mt-20 md:mt-[120px] lg:mt-[190px] md:text-[34px] lg:text-[40px] font-bold'>
          There is no Comments
        </h1>
      )}
      <div className='flex flex-col gap-3 md:gap-5 mt-5 max-h-[80vh] overflow-y-auto px-2 md:px-5'>
        {allComments?.content?.map(
          ({ id, createdDate, email, content, enabled }) => (
            <div
              key={id}
              className='flex flex-col p-2 lg:p-4 border border-gray-700 bg-slate-100 text-[15px] font-normal md:text-[16px]'
            >
              <p>
                Id: <span>{id}</span>
              </p>
              <p>
                Posted : <span>{new Date(createdDate).toDateString()}</span>
              </p>
              <p>
                Content: <span>{content}</span>
              </p>
              <p>
                User Email: <span>{email}</span>
              </p>
              <p>
                Enabled:{' '}
                <span
                  className={` ${enabled ? 'text-green-700' : 'text-red-700'}`}
                >
                  {enabled ? 'Enabled' : 'Disabled'}
                </span>
              </p>
              <button
                className={`bg-gray-600 text-white hover:bg-gray-700 transition-all duration-300 outline-none focus:ring focus:ring-opacity-80 focus:ring-gray-400 max-w-[60px] shadow-md flex items-center justify-center h-6 lg:h-7 text-[13px] lg:text-[15px] ${
                  activeComment === id && 'hidden'
                }`}
                onClick={() => setactiveComment(() => id)}
              >
                Actions
              </button>
              <div
                className={` flex gap-1 transition-all duration-300 ${
                  activeComment === id ? 'scale-100' : ' scale-0 h-0 w-0 p-0'
                }`}
              >
                <button
                  disabled={isDeleting}
                  className={`bg-red-600 p-1 text-white hover:bg-red-700 transition-all duration-300 outline-none focus:ring focus:ring-opacity-80 focus:ring-red-400 max-w-[60px] shadow-md flex items-center justify-center h-6 lg:h-7 text-[13px] lg:text-[15px] `}
                  onClick={() => deleteComment(id)}
                >
                  Delete
                </button>
                <button
                  disabled={isEnabling || isDisabling}
                  className={`bg-yellow-600 p-1 text-white hover:bg-yellow-700 transition-all duration-300 outline-none focus:ring focus:ring-opacity-80 focus:ring-yellow-400 max-w-[60px] shadow-md flex items-center justify-center h-6 lg:h-7 text-[13px] lg:text-[15px] `}
                  onClick={() =>
                    enabled ? disableComment(id) : enableComment(id)
                  }
                >
                  {enabled ? 'Disable' : 'Enable'}
                </button>
              </div>
            </div>
          )
        )}
      </div>
      <Pagination totalPages={allComments?.totalPages} />
    </div>
  );
};

export default CommentsList;
