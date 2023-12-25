import { useMutation, useQueryClient } from '@tanstack/react-query';
import { createCategory as createCategoryApi } from '../../../api/actions';
import toast from 'react-hot-toast';

export function useCreateCategory() {
  const queryClient = useQueryClient();
  const { mutate: createCategory, isLoading: isCreating } = useMutation({
    mutationFn: (category) => createCategoryApi(category),
    onSuccess: (response) => {
      if (response.status === 201) {
        toast.success('Category has been created');
        queryClient.invalidateQueries(['categories']);
      } else {
        toast.error(response);
      }
    },
  });
  return { createCategory, isCreating };
}
