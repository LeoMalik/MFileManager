package com.example.leo.mfilemanager.encrypt; /**
 * @Author:Leo
 * @Description
 * @Date: Created in 15:31 2018/3/8
 * @Modified
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 路很长~ on 2018/1/10.
 */

public class encrypt {

    public String key;



    public encrypt(String key){
        this.key=key;
    }
    public static byte[] jiami(byte[] ary,boolean choose) {
        // TODO Auto-generated method stub

        byte[] bytekey = {115,101,99,117,114,105,116,121,99,111,109,112,117,116,101,114};//密钥
        int splitSize = 8;//分割的块大小
        Object[] subAry = splitAry(ary, splitSize);//分割后的子块数组
        IDEAChange idea = new IDEAChange();
        byte[] encryptdata=null;
        byte[] decryptdata=null;
        byte[] finaldata=new byte[ary.length];
        if (choose==true) {
            for (int j = 0; j < subAry.length; j++) {
                Object obj = subAry[j];
                byte[] aryItem = (byte[]) obj;
                if (j != subAry.length - 1) {
                    encryptdata = idea.IdeaEncrypt(bytekey, aryItem, true);
                    for (int i = 0; i < encryptdata.length; i++) {
//                    System.out.print(encryptdata[i] + ", ");
                        finaldata[j * 8 + i] = encryptdata[i];
                    }


                } else {
                    for (int i = 0; i < aryItem.length; i++) {
//                        System.out.print(aryItem[i] + ", ");
                        finaldata[j * 8 + i] = aryItem[i];
                    }
                }
            }

        }
        if (choose==false){
            for (int j = 0; j < subAry.length; j++){
                Object obj = subAry[j];
                byte[] aryItem = (byte[]) obj;
                if (j != subAry.length - 1){
                    decryptdata = idea.IdeaEncrypt(bytekey, aryItem, false);
                    for (int i = 0; i < decryptdata.length; i++) {
//                    System.out.print(encryptdata[i] + ", ");
                        finaldata[j * 8 + i] = decryptdata[i];
                    }
                }else {
                    for (int i = 0; i < aryItem.length; i++) {
//                        System.out.print(aryItem[i] + ", ");
                        finaldata[j * 8 + i] = aryItem[i];
                    }
                }

            }
        }
        return finaldata;

    }


    /**
     * splitAry方法<br>
     * @param ary 要分割的数组
     * @param subSize 分割的块大小
     * @return
     *
     */
    public static Object[] splitAry(byte[] ary, int subSize) {
        int count = ary.length % subSize == 0 ? ary.length / subSize: ary.length / subSize + 1;

        List<List<Byte>> subAryList = new ArrayList<List<Byte>>();

        for (int i = 0; i < count; i++) {
            int index = i * subSize;
            List<Byte> list = new ArrayList<Byte>();
            int j = 0;
            while (j < subSize && index < ary.length) {
                list.add(ary[index++]);
                j++;
            }
            subAryList.add(list);
        }

        Object[] subAry = new Object[subAryList.size()];

        for(int i = 0; i < subAryList.size(); i++){

            List<Byte> subList = subAryList.get(i);
//            if (subList == null || subList.size() < 0)
//                return null;
//            byte[] bytes = new byte[subList.size()];
//            int k = 0;
//            Iterator<Byte> iterator = subList.iterator();
//            while (iterator.hasNext()) {
//                bytes[k] = iterator.next();
//                k++;
//            }

            byte[] subAryItem = new byte[subList.size()];
            for(int j = 0; j < subList.size(); j++){
                subAryItem[j] = subList.get(j);
            }
            subAry[i] = subAryItem;
        }

        return subAry;
    }
}