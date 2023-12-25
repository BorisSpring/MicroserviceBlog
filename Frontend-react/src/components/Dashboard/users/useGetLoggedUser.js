import { useQuery } from '@tanstack/react-query';
import { getLoggedUser } from '../../../api/actions';

export function useGetLoggedUser() {
  const { data: loggedUser, isLoading } = useQuery({
    queryFn: () => getLoggedUser(),
    queryKey: [localStorage.getItem('jwt')],
  });
  return { loggedUser, isLoading };
}
