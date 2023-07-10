package dungnt.ptit.myspringsocial.pojo;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
class Solution {
    boolean [] numsChecked;
    public int[] twoSum(int[] nums, int target) {
        initValue(nums);
        return backtrack(nums,target,0,this.numsChecked);
    }

    public void initValue(int[] nums){
        numsChecked = new boolean[nums.length];
    }

    public int[] backtrack(int[] nums, int target, int current, boolean []numsCheck){
        int myCurrent = current;
        boolean []myNumsChecked = numsCheck;
        for(int i=0 ; i<nums.length ; ++i){
            if(!myNumsChecked[i]){
                myCurrent += nums[i];
                if(myCurrent == target){
                    myNumsChecked[i] = true;
                    return getNumsChecked();
                }
                else{
                    if(myCurrent < target){
                        myNumsChecked[i] = true;
                        return backtrack(nums, target, myCurrent,myNumsChecked);
                    }
                    // Trường hợp trả lại giá trị rất quan trọng, ( đặt myCurrent ko tác dụng gì cả)
                    int t=i;
                    if(i>=1) t=i-1;
                    myCurrent -= nums[t];
                    myNumsChecked[t] = false;
                }
            } 
        }
        int []result = {};
        return result;
    }

    public int[] getNumsChecked(){
        List<Integer> result = new ArrayList<>();
        for(int i=0 ; i<this.numsChecked.length ; ++i){
            if(numsChecked[i]){
               result.add(i);
            }
        }
        return result.stream().mapToInt(value -> value).toArray();
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        int []nums = {3,2,4};
        int []result = s.twoSum(nums,6);
        for(int i=0 ; i<result.length ; ++i){
            System.out.print(result[i]);
        }

        log.info("xx {} xx {} xx ${num[1]}" , "hê hê","dũng");
//        Integer x=4;
//        Integer b=x;
//        b=5;
//        System.out.println(x+"_"+b);
    }

}