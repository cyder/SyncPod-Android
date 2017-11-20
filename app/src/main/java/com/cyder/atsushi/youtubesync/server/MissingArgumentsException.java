package com.cyder.atsushi.youtubesync.server;

/**
 * ルーム作る際のエラー処理用例外
 * ただそれだけのためにこのクラスを作るのもばかばかしいので何かあれば教えて欲しいです
 * Created by kousuke nezu on 2017/11/20.
 */

public class MissingArgumentsException extends Exception {
    public static final String MISSING_NAME_ARGUMENT = "Missing name argument.";
    public static final String MISSING_DESCRIPTION_ARGUMENT = "Missing description argument.";
    public static final String MISSING_BOTH_ARGUMENTS = "Missing both arguments.";

    public MissingArgumentsException(String missingArgument) {
        super(missingArgument);
    }
}
