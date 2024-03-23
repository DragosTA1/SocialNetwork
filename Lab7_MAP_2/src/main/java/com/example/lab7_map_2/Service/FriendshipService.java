package com.example.lab7_map_2.Service;


import com.example.lab7_map_2.Domain.Friendship;
import com.example.lab7_map_2.Domain.Tuple;
import com.example.lab7_map_2.Domain.User;
import com.example.lab7_map_2.Repository.FriendshipRepository;
import com.example.lab7_map_2.Repository.Repository;
import com.example.lab7_map_2.Validator.Validator;
import com.example.lab7_map_2.utils.events.FriendshipChangeEvent;
import com.example.lab7_map_2.utils.observer.Observable;
import com.example.lab7_map_2.utils.observer.Observer;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class FriendshipService implements Observable<FriendshipChangeEvent> {
    private final FriendshipRepository friendshipRepo;
    private final Repository<Long, User> userRepo;
    private final Validator<Friendship> val;
    private final List<Observer<FriendshipChangeEvent>> observers = new ArrayList<>();


    public FriendshipService(FriendshipRepository friendshipRepo, Repository<Long, User> userRepo, Validator<Friendship> val) {
        this.friendshipRepo = friendshipRepo;
        this.userRepo = userRepo;
        this.val = val;
    }


    public Friendship findOne(Tuple<Long, Long> id ) {
        Optional<Friendship> fr_op = friendshipRepo.findOne(id);
        if (fr_op.isEmpty())
            throw new IllegalArgumentException("The friendship you are looking for doesn't exist");
        else
            return fr_op.get();
    }


    public Iterable<Friendship> getAll() {
        return friendshipRepo.getAll();
    }


    public void add(Long idUser1, Long idUser2){
        if (userRepo.findOne(idUser1).isEmpty() || userRepo.findOne(idUser2).isEmpty())
            throw new IllegalArgumentException("User ids must be existing and valid");
        Friendship friendship = new Friendship(new Tuple<>(idUser1, idUser2));
        val.validate(friendship);
        Optional<Friendship> fr_op = friendshipRepo.add(friendship);
        if (fr_op.isPresent())
            throw new IllegalArgumentException("Users are already friends\n" );
    }


    public void delete(Long idUser1, Long idUser2){
        Optional<Friendship> fr_op = friendshipRepo.findOne(new Tuple<>(idUser1, idUser2));
        friendshipRepo.delete(new Tuple<>(idUser1, idUser2));
        if (fr_op.isEmpty())
            throw new IllegalArgumentException("The friendship you are looking for doesn't exist");
    }


    public void update(Long idUser1, Long idUser2, LocalDate date) {
        Friendship updatedFriendship = new Friendship(new Tuple<>(idUser1, idUser2), date);
        val.validate(updatedFriendship);
        Optional<Friendship> fr_op = friendshipRepo.update(updatedFriendship);
        if (fr_op.isEmpty())
            throw new IllegalArgumentException("There is no friendship between the entered IDs.");
    }


    private void dfs(Long v, HashMap<Long, Boolean> visited) {
        Stack<Long> stack = new Stack<>();
        stack.push(v);

        while (!stack.isEmpty()) {
            Long node = stack.pop();//scoatem ultimul nod adaugat (id-ul user-ului)
            if (!visited.get(node)) {
                visited.put(node, true); // Marcam nodul ca vizitat
                friendshipRepo.findFriends(node).forEach(neighbor -> {
                    if (!visited.get(neighbor)) {
                    stack.push(neighbor); // Adăugăm vecinii nevizitați în stivă pentru a-i explora
                    }
                });
//                for (Long neighbor : friendshipRepo.findFriends(node)) {
//                    if (!visited.get(neighbor)) {
//                        stack.push(neighbor); // Adăugăm vecinii nevizitați în stivă pentru a-i explora
//                    }
//                }
            }
        }
    }


    public int numberOfComunities() {
        int countCommunities = 0;
        HashMap<Long, Boolean> visited = new HashMap<>();
        for (User user : userRepo.getAll()) {
            visited.put(user.getId(), false);
        }
        for (User user : userRepo.getAll()) {
            if (!visited.get(user.getId())) {
                dfs(user.getId(), visited);
                countCommunities++;
            }
        }
        return countCommunities;
    }


    public Iterable<Long> findLongestPathFromNode(Long source) {
        Map<Long, Integer> distances = new HashMap<>(); //map pt distante(id-nod si distanta de la nodul sursa
        //friendshipRepo.findFriends(source).forEach(neighbor -> distances.put(neighbor, -1));
        for (User node : userRepo.getAll()) {
            distances.put(node.getId(), -1);
        }
        distances.put(source, 0);
        Stack<Long> stack = new Stack<>();
        stack.push(source);

        while (!stack.isEmpty()) {
            Long node = stack.pop();
            friendshipRepo.findFriends(node).forEach(neighbor -> {
                if (distances.get(neighbor) == -1) {
                    distances.put(neighbor, distances.get(node) + 1);
                    stack.push(neighbor);
                }
            });
//            for (Long neighbor : friendshipRepo.findFriends(node)) {
//                if (distances.get(neighbor) == -1) {
//                    distances.put(neighbor, distances.get(node) + 1);
//                    stack.push(neighbor);
//                }
//            }
        }

        // Găsiți nodul cu distanța maximă
        Long maxDistanceNode = source;
        int maxDistance = 0;
        for (Map.Entry<Long, Integer> entry : distances.entrySet()) {
            if (entry.getValue() > maxDistance) {
                maxDistance = entry.getValue();
                maxDistanceNode = entry.getKey();
            }
        }

        // Reconstituiți cel mai lung drum
        List<Long> longestPath = new ArrayList<>();
        longestPath.add(maxDistanceNode);
        Long current = maxDistanceNode;
        while (distances.get(current) > 0) {
            //mergem din aproape in aproape inapoi pe nodurile cu distanta cu o unitate mai mica
            for (Long neighbor : friendshipRepo.findFriends(current)) {
                System.out.println(current +"rdfes" + neighbor);
                if ( distances.get(neighbor) == distances.get(current) - 1) {
                    //if (distances.containsKey(neighbor) && distances.get(neighbor) == distances.get(current) - 1) {
                    longestPath.add(neighbor);
                    current = neighbor; // trecem pe codul precedent
                    break;
                }
            }
        }
        return longestPath;
    }


    public Iterable<Long> findTheMostSociableComunity() {
        List<Long> longestPath = new ArrayList<>();

        // Inițializăm o variabilă pentru a urmări lungimea drumului maxim
        int maxLength = -1;

        // Iterăm peste fiecare nod din comunitrate și căutăm cel mai lung drum
        for (User user : userRepo.getAll()) {
            List<Long> path = (ArrayList<Long>)findLongestPathFromNode(user.getId());
            if (path.size() > maxLength) {
                maxLength = path.size();
                longestPath = path;
            }
        }

        return longestPath;
    }

    public List<Friendship> allFriendshipsFromAMonth(Long id, int m, int y) {
//        ArrayList<Friendship> friendships = new ArrayList<>();
//        for (Friendship friendship : friendshipRepo.getAll()) {
//            System.out.println(friendship.getDate());
//            if (friendship.getDate().getMonthValue() == m &&
//                    friendship.getDate().getYear() == y) {
//                friendships.add(friendship);
//            }
//        }
        List<Friendship> filteredFriendships = (List<Friendship>) friendshipRepo.getAll();
        return filteredFriendships
                .stream()
                .filter(friendship ->
                        (Objects.equals(friendship.getId().getLeft(), id) || Objects.equals(friendship.getId().getRight(), id)) &&
                        friendship.getDate().getMonthValue() == m && friendship.getDate().getYear() == y)
                .collect(Collectors.toList());
    }

    @Override
    public void addObserver(Observer<FriendshipChangeEvent> e) {observers.add(e);}

    @Override
    public void removeObserver(Observer<FriendshipChangeEvent> e) {observers.remove(e);}

    @Override
    public void notifyObservers(FriendshipChangeEvent t) {observers.forEach(o -> o.update(t));}
}
