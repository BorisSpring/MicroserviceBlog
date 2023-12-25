import { useQuery } from '@tanstack/react-query';
import { getAllMessages } from '../../../api/actions';
import toast from 'react-hot-toast';
import { useGetParams } from '../../../hooks/useGetParams';

export function useGetAllMessages() {
  const params = useGetParams();
  const { data: allMessages, isLoading } = useQuery({
    queryFn: async () => await getAllMessages(params),
    queryKey: [params.toString(), 'messages'],
    onError: (err) => {
      toast.error(err.message);
    },
  });

  return { allMessages, isLoading };
}
