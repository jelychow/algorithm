import java.util.*;

class RandomizedCollection {

                        Map<Integer, Set<Integer>> map;
                        List<Integer> list;

                        /**
                         * Initialize your data structure here.
                         */
                        public RandomizedCollection() {
                            map = new HashMap<>();
                            list = new ArrayList<>();
                        }

                        /**
                         * Inserts a value to the collection. Returns true if the collection did not already contain the specified element.
                         */
                        public boolean insert(int val) {
                            list.add(val);
                            Set set = map.getOrDefault(val, new HashSet<>());
                            set.add(list.size() - 1);
                            map.put(val,set);
                            return set.size()==1;
                        }

                        /**
                         * Removes a value from the collection. Returns true if the collection contained the specified element.
                         */
                        public boolean remove(int val) {
                            /**
                             *  算法执行过程
                              */


                            Set set =  map.get(val);
                            if(set==null){
                                return false;
                            }
                            Iterator iterator = set.iterator();
                            // 需要被移除的 index
                            int index = (int) iterator.next();
                            // 把最后一个数字移动过来
                            // 交换位置 交换坐标 删除 最后一个元素
                            int lastVal = list.get(list.size()-1);
                            list.set(index,lastVal);
                            map.get(val).remove(index);
                            if (index!=list.size()-1){
                                map.get(lastVal).add(index);
                                // 没必要多删除一次
                                map.get(lastVal).remove(list.size()-1);
                            }

                            if(map.get(val).isEmpty()){
                                map.remove(val);
                            }
                            list.remove(list.size()-1);
                            return true;
                        }

                        /**
                         * Get a random element from the collection.
                         */
                        public int getRandom() {
                            return list.get((int) (Math.random() * list.size()));
                        }
                    }