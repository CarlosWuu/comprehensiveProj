package com.wuu.tech.commons.util;

/**
 * 大通运维工具类
 * @author wujiabin
 */
public class OperateUtil {

    /**
     * 手动生成替换链sql
     * @param partNos eg:C00054199-2048	C00421043
     */
    public static void generatePartLinkSql(String partNos){

        String[] partNoSet = partNos.split("\t");

        String partLinkedSql= "INSERT INTO PART_LINKED (\"ID\", \"LINKED_VALUE\", \"LINKED_LENGTH\", \"CREATED_TIME\", \"UPDATED_TIME\") VALUES ('6879852', '%s', %d, TO_TIMESTAMP('2022-11-29 02:01:18.448000', 'SYYYY-MM-DD HH24:MI:SS:FF6'), NULL);";

        System.out.println(String.format(partLinkedSql,partNos,partNoSet.length));

        String partLinkedRelationSql = "INSERT INTO PART_LINKED_RELATION (\"ID\", \"PART_NO\", \"PART_LINKED_ID\", \"CREATED_TIME\", \"RANKING\", \"HEAD_FLAG\", \"TAIL_FLAG\", \"RECOMMEND_FLAG\") VALUES\n" +
                " (PART_LINKED_RELATION_ID.NEXTVAL, '%s', '6878318', TO_TIMESTAMP('2022-11-29 02:01:18.448000', 'SYYYY-MM-DD HH24:MI:SS:FF6'), %d, %d, %d, '0');";

        for(int i=0;i<partNoSet.length;i++){
            String partNo = partNoSet[i];
            int headFlag=0;
            if(i==0){
                headFlag=1;
            }
            int tailFlag = 0 ;
            if(i == partNoSet.length-1){
                tailFlag = 1;
            }
            System.out.println(String.format(partLinkedRelationSql,partNo,i+1,headFlag,tailFlag));
        }
    }
}
