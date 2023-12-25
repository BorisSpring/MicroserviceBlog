import React from 'react';
import { Link } from 'react-router-dom';
import { format } from 'date-fns';
import { motion } from 'framer-motion';
//custom hooks
import { useGeAllCategories } from './Dashboard/categories/useGetAllCategories';
import { useFind3NewestBlogs } from '../hooks/useFind3newestBlogs';
import { login } from '../utils/auth_helper';

const Footer = () => {
  const { allCategories } = useGeAllCategories();
  const { newestThree } = useFind3NewestBlogs();
  return (
    <div className='bg-[#0a0b0c] mt-auto'>
      <footer className='container mx-auto  text-white flex flex-col lg:flex-row py-10 px-5 lg:justify-between text-[13px] md:text-[15px] gap-5'>
        <div className='flex flex-col gap-[6.5px] '>
          <h6 className='font-bold text-[20px]'>Make By Boris</h6>
          <p className=' hover:underline transition-all duration-300'>
            Adress: Bistricka 4, Belgrade, Serbia
          </p>
          <p className=' hover:underline transition-all duration-300'>
            Phone: +381-62-9256-229
          </p>
          <p>
            Email:{' '}
            <a href='mailto:borisdimitrijevicit@gmail.com' className='border-b'>
              borisdimitrijevicit@gmail.com
            </a>
          </p>
          <button
            onClick={() => login()}
            className='text-left hover:underline transition-all duration-300'
          >
            Login
          </button>
        </div>
        <div className='grid grid-cols-2 gap-x-3 w-[40%] gap-y-3 lg:w-fit h-fit'>
          {allCategories?.map?.(({ name }) => (
            <Link
              to={`/blogs?category=${name.replaceAll(' ', '-')}`}
              key={name}
              className='capitalize   hover:underline transition-all duration-300'
            >
              {name}
            </Link>
          ))}
        </div>
        <div className='flex flex-col gap-[6.5px]'>
          {newestThree?.map(({ id, title, created, blogImage }) => (
            <Link
              key={id}
              to={`/blog/${id}/${title.toLowerCase().replaceAll(' ', '-')}`}
              className='flex gap-2 items-center text-[13px] hover:underline transition-all duration-300'
            >
              <div className='p-1 md:-2 border-2 border-slate-500'>
                <div className='w-10 h-10 overflow-hidden'>
                  <motion.img
                    whileHover={{ scale: 1.25 }}
                    src={`data:image/png;base64,${blogImage}`}
                    alt='Its describing writed blog on our site'
                    className='w-10 h-10 object-contain '
                  />
                </div>
              </div>
              <div className='flex flex-col '>
                <p>{title?.length > 30 ? `${title.slice(0, 30)}...` : title}</p>
                <p>{format(new Date(created), 'MMM, dd, yyyy')}</p>
              </div>
            </Link>
          ))}
        </div>
      </footer>
    </div>
  );
};

export default Footer;
