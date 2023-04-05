package com.cheng.common.error.manager;

import com.google.common.base.Preconditions;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.cheng.common.error.api.IErrorCode;
import com.cheng.common.error.api.IProjectModule;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * @time: 2022/11/16 14:31
 * @author: licheng
 * @desc: 异常管理
 */
public class ErrorManager {

    /**
     * BiMap 一种映射数据结构，key和value都是唯一的，可以通过key获取唯一的value
     * 通过 value获取 key 则需要 调用 inverse 反转 value和key 后 通过 get(value)获取 key
     * 全局错误代码码表
     * 防止多个模块出现重复的 错误代码
     *
     */
    private static final BiMap<Integer, IErrorCode> GLOBAL_ERROR_CODE_MAP = HashBiMap.create();


    private static final Map<IErrorCode, IProjectModule> ERROR_PROJECT_MODULE_MAP = new ConcurrentHashMap<>();

    private static final Comparator<IProjectModule> PROJECT_MODULE_COMPARATOR = Comparator.comparingInt(IProjectModule::getProjectCode);

    private static final Comparator<IErrorCode> ERROR_CODE_COMPARATOR = Comparator.comparingInt(IErrorCode::getCode);

    /**
     * 项目及模块注册
     * @param IProjectModule
     * @param errorCode
     */
    public static void register(IProjectModule IProjectModule, IErrorCode errorCode){
        Preconditions.checkNotNull(IProjectModule);
        Preconditions.checkArgument(IProjectModule.getProjectCode() >=0);
        Preconditions.checkArgument(IProjectModule.getModuleCode() >=0);
        Preconditions.checkArgument(errorCode.getNodeNum() >=0);
        int code = generateCode(IProjectModule,errorCode);
        Preconditions.checkArgument(!GLOBAL_ERROR_CODE_MAP.containsKey(code),"com.cheng.common.error code duplicate: " + code);
        GLOBAL_ERROR_CODE_MAP.put(code,errorCode);
        ERROR_PROJECT_MODULE_MAP.put(errorCode, IProjectModule);
    }


    public static List<TreeNode> getAllErrorCodes() {
        return ERROR_PROJECT_MODULE_MAP.entrySet().stream()
                .sorted((it1, it2) -> ERROR_CODE_COMPARATOR.compare(it1.getKey(), it2.getKey()))
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
                .entrySet()
                .stream()
                .sorted((it1, it2) -> PROJECT_MODULE_COMPARATOR.compare(it1.getKey(), it2.getKey()))
                .collect(Collectors.groupingBy(
                                e -> new TreeNode(e.getKey().getProjectCode(), e.getKey().getProjectName()),
                                Collectors.groupingBy(
                                        it -> new TreeNode(it.getKey().getModuleCode(), it.getKey().getModuleName()),
                                        Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                                )
                        )
                )
                .entrySet()
                .stream()
                .map(e -> {
                    TreeNode top = e.getKey();
                    List<TreeNode> middleNode = e.getValue()
                            .entrySet()
                            .stream()
                            .map(e1 -> {
                                TreeNode key = e1.getKey();
                                List<TreeNode> leftNode = e1.getValue().stream()
                                        .flatMap(Collection::stream)
                                        .map(errorCode -> new TreeNode(errorCode.getCode(), errorCode.getMsg()))
                                        .collect(Collectors.toList());
                                key.setNodes(leftNode);
                                return key;
                            })
                            .collect(Collectors.toList());
                    top.setNodes(middleNode);
                    return top;
                })
                .collect(Collectors.toList());
    }


    /**
     * 生成完整的错误码
     * @param IProjectModule 项目以及模块
     * @param errorCode 错误码
     * @return
     */
    private static int generateCode(IProjectModule IProjectModule, IErrorCode errorCode){
         // 万位以上为 项目标识  百位以上为 模块标识  百位以下的为项目中的错误码
        return IProjectModule.getProjectCode() * 10000 + IProjectModule.getModuleCode() * 100 + errorCode.getNodeNum();
    }

    /**
     * 获取完整的错误码
     * @param errorCode
     * @return
     */
    public static int generateCode(IErrorCode errorCode){
         return GLOBAL_ERROR_CODE_MAP.inverse().get(errorCode);
    }

    /**
     * 根据错误码获取项目及模块名称
     * @param errorCode
     * @return
     */
    public static IProjectModule projectModule(IErrorCode errorCode){
        return ERROR_PROJECT_MODULE_MAP.get(errorCode);
    }

}
