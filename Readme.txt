This is schedule management application for Android.
Currently it is a beta version, so there is no notification setting function etc.

【コード解説】
Schedule(データベース本体)
//データベースの名前や列名を記述する

ScheduleDao
//Repositoryから送られてくる命令を基に、データベースへ実際に送信するSQL文を記述する

ScheduleRoomDatabase
//データベースのインスタンスの作成や保持を行う

ScheduleRepository
//データベースとViewModelの中間に位置し、データベースに命令を出したりViewModelに
結果を照会する

ScheduleViewModel
//UIとRepositoryの間に位置し、Repositoryから受け取ったデータをUIに渡したり、UIの
状態を保持したりする

ScheduleListAdapter
//RecyclerViewを操作するクラス

DatePickerFragment
//日付を設定するフラグメント、MakeScheduleActivity内でのみ起動する。
TimePickerFragment
//時刻を設定するフラグメント、MakeScheduleActivity内でのみ起動する。

MainActivity
//メイン画面を作るクラス

MakeScheduleActivity
//予定作成用アクティビティ
