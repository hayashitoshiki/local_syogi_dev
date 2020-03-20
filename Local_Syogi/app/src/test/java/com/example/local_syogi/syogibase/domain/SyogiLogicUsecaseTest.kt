package com.example.local_syogi.syogibase.domain

import com.example.local_syogi.syogibase.data.repository.BoardRepository
import com.example.local_syogi.syogibase.data.game.Cell
import com.example.local_syogi.syogibase.util.Piece
import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.mockito.ArgumentMatchers.anyInt
import org.powermock.api.mockito.PowerMockito



class SyogiLogicUsecaseTest {

    @Before
    fun setUp() {
    }


    /**
     * 盤上の自分の駒をタップしたときにヒントを表示する
     * 条件：中央の王(周りなし)をタップ
     * 期待結果：8回setHint()が呼ばれる
     */
    @Test
    fun setTouchHintCenter(){
        val repository = mock<BoardRepository>{
            on{ getMove(4,4) } doReturn Piece.OU.getMove()
            on{ findKing(anyInt()) } doReturn Pair(0,0)
            on{ getPiece(anyInt(), anyInt())} doReturn Piece.None
            on{ getTurn(anyInt(),anyInt())  } doReturn 2
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setTouchHint(4,4)
        verify(repository, times(8)).setBackMove()
    }

    /**
     * 盤上の自分の駒をタップしたときにヒントを表示する
     * 条件：左上の王(周りなし)をタップ
     * 期待結果：3回setHint()が呼ばれる
     */
    @Test
    fun setTouchHintTop(){
        val repository = mock<BoardRepository>{
            on{ getMove(0,0) } doReturn Piece.OU.getMove()
            on{ findKing(anyInt()) } doReturn Pair(0,0)
            on{ getPiece(anyInt(), anyInt())} doReturn Piece.None
            on{ getTurn(anyInt(),anyInt())  } doReturn 2
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setTouchHint(0,0)
        verify(repository, times(3)).setBackMove()
    }

    /**
     * 盤上の自分の駒をタップしたときにヒントを表示する
     * 条件：右下端の王(周りなし)をタップ
     * 期待結果：3回setHint()が呼ばれる
     */
    @Test
    fun setTouchHintBottom(){
        val repository = mock<BoardRepository>{
            on{ getMove(8,8) } doReturn Piece.OU.getMove()
            on{ findKing(anyInt()) } doReturn Pair(0,0)
            on{ getPiece(anyInt(), anyInt())} doReturn Piece.None
            on{ getTurn(anyInt(),anyInt())  } doReturn 2
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setTouchHint(8,8)
        verify(repository, times(3)).setBackMove()
    }

    /**
     * 盤上の自分の駒をタップしたときにヒントを表示する
     * 条件：中央の王(周り全部自分の駒)をタップ
     * 期待結果：8回setHint()が呼ばれる
     */
    @Test
    fun setTouchHintCenter2(){
        val repository = mock<BoardRepository>{
            on{ getMove(4,4) } doReturn Piece.OU.getMove()
            on{ findKing(anyInt()) } doReturn Pair(0,0)
            on{ getPiece(anyInt(), anyInt())} doReturn Piece.None
            on{ getTurn(anyInt(),anyInt())  } doReturn 1
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setTouchHint(4,4)
        verify(repository, times(0)).setBackMove()
    }

     /**
     * ヒントを設定する関数
     * 条件；対象のマスに動かそうとしても、王手されていない
     * 結果：ヒントがセットされる
     */
    @Test
    fun setHintTrue() {
        //テストクラス作成
        val repository = mock<BoardRepository>{
            on{findKing(1)} doReturn Pair(5,5)
            on{getPiece(anyInt(), anyInt())} doReturn Piece.None
            on{getTurn(anyInt(),anyInt())} doReturn 1
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        val method = PowerMockito.method(useCase.javaClass, "setHint", Int::class.java,Int::class.java,Int::class.java,Int::class.java,Int::class.java)
        //実行
        method.invoke(useCase, 3,3,2,3,1)
        verify(repository, times(1)).setHint(2,3)
    }

    /**
     * ヒントを設定する関数
     * 条件；対象のマスに動かそうとすると、王手される
     * 結果：ヒントがセットされる
     */
    @Test
    fun setHintFalse() {
        //テストクラス作成
        val repository = mock<BoardRepository>{
            on{findKing(1)} doReturn Pair(5,5)
            on{getPiece(anyInt(), anyInt())} doReturn Piece.RYU
            on{getTurn(anyInt(),anyInt())} doReturn 2
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        val method = PowerMockito.method(useCase.javaClass, "setHint", Int::class.java,Int::class.java,Int::class.java,Int::class.java,Int::class.java)
        //実行
        method.invoke(useCase, 3,3,2,3,1)
        verify(repository, times(0)).setHint(2,3)
    }

    //持ち駒の打てる場所判定
    /**
     * 持ち駒の打てる場所判定関数
     * 条件；歩をタップした場合
     * 結果：72回useCaseのsetHint()が呼ばれる
     */
    @Test
    fun getHintHoldPieceFU() {
        val repository = mock<BoardRepository>{
            on{findHoldPieceBy(anyInt(),anyInt())} doReturn Piece.FU
            on{ findKing(anyInt()) } doReturn Pair(5,5)
            on{ getPiece(anyInt(),anyInt()) } doReturn Piece.None
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setHintHoldPiece(5,10)
        verify(repository, times(72)).setBackMove()
    }

    /**
     * 持ち駒の打てる場所判定関数
     * 条件；香車をタップした場合
     * 結果：72回useCaseのsetHint()が呼ばれる
     */
    @Test
    fun getHintHoldPieceKYO() {
        val repository = mock<BoardRepository>{
            on{findHoldPieceBy(anyInt(),anyInt())} doReturn Piece.KYO
            on{ findKing(anyInt()) } doReturn Pair(5,5)
            on{ getPiece(anyInt(),anyInt()) } doReturn Piece.None
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setHintHoldPiece(5,10)
        verify(repository, times(72)).setBackMove()
    }

    /**
     * 持ち駒の打てる場所判定関数
     * 条件；桂馬をタップした場合
     * 結果：72回useCaseのsetHint()が呼ばれる
     */
    @Test
    fun getHintHoldPieceKEI() {
        val repository = mock<BoardRepository>{
            on{findHoldPieceBy(anyInt(),anyInt())} doReturn Piece.KEI
            on{ findKing(anyInt()) } doReturn Pair(5,5)
            on{ getPiece(anyInt(),anyInt()) } doReturn Piece.None
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setHintHoldPiece(5,10)
        verify(repository, times(63)).setBackMove()
    }

    /**
     * 持ち駒の打てる場所判定関数
     * 条件；銀をタップした場合
     * 結果：781回useCaseのsetHint()が呼ばれる
     */
    @Test
    fun getHintHoldPieceGIN() {
        val repository = mock<BoardRepository>{
            on{findHoldPieceBy(anyInt(),anyInt())} doReturn Piece.GIN
            on{ findKing(anyInt()) } doReturn Pair(5,5)
            on{ getPiece(anyInt(),anyInt()) } doReturn Piece.None
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setHintHoldPiece(5,10)
        verify(repository, times(81)).setBackMove()
    }

    /**
     * 持ち駒の打てる場所判定関数
     * 条件；金をタップした場合
     * 結果：81回useCaseのsetHint()が呼ばれる
     */
    @Test
    fun getHintHoldPieceKIN() {
        val repository = mock<BoardRepository>{
            on{findHoldPieceBy(anyInt(),anyInt())} doReturn Piece.KIN
            on{ findKing(anyInt()) } doReturn Pair(5,5)
            on{ getPiece(anyInt(),anyInt()) } doReturn Piece.None
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setHintHoldPiece(5,10)
        verify(repository, times(81)).setBackMove()
    }

    /**
     * 持ち駒の打てる場所判定関数
     * 条件；飛車をタップした場合
     * 結果：81回useCaseのsetHint()が呼ばれる
     */
    @Test
    fun getHintHoldPieceHISYA() {
        val repository = mock<BoardRepository>{
            on{findHoldPieceBy(anyInt(),anyInt())} doReturn Piece.HISYA
            on{ findKing(anyInt()) } doReturn Pair(5,5)
            on{ getPiece(anyInt(),anyInt()) } doReturn Piece.None
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setHintHoldPiece(5,10)
        verify(repository, times(81)).setBackMove()
    }

    /**
     * 持ち駒の打てる場所判定関数
     * 条件；角をタップした場合
     * 結果：81回useCaseのsetHint()が呼ばれる
     */
    @Test
    fun getHintHoldPieceKAKU() {
        val repository = mock<BoardRepository>{
            on{findHoldPieceBy(anyInt(),anyInt())} doReturn Piece.KAKU
            on{ findKing(anyInt()) } doReturn Pair(5,5)
            on{ getPiece(anyInt(),anyInt()) } doReturn Piece.None
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setHintHoldPiece(5,10)
        verify(repository, times(81)).setBackMove()
    }

    /**
     * 持ち駒の打てる場所判定関数
     * 条件；空をタップした場合
     * 結果：0回useCaseのsetHint()が呼ばれる
     */
    @Test
    fun getHintHoldPieceNone() {
        val repository = mock<BoardRepository>{
            on{findHoldPieceBy(anyInt(),anyInt())} doReturn Piece.None
            on{ findKing(anyInt()) } doReturn Pair(5,5)
            on{ getPiece(anyInt(),anyInt()) } doReturn Piece.None
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setHintHoldPiece(5,10)
        verify(repository, times(0)).setBackMove()
    }



    //動かす
    /**
     * 駒を動かす関数
     * 正常系：下記のメソッドを呼ぶ
     * ・駒を動かすBoardRepositoryメソッド
     * ・持ち駒をセットするBoardRepositoryメソッド
     * ・ヒントをリセットするメソッド
     */
    @Test
    fun setMove() {
        //テストクラス作成
        val repository = mock<BoardRepository>{}
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.setMove(3,3,false)
        verify(repository, times(1)).setMove(eq(3),eq(3),anyInt(),eq(false))
        verify(repository, times(1)).setHoldPiece()
        verify(repository, times(1)).resetHint()
    }

    /**
     * 成り判定メソッド
     * 条件：成れるマスタップ
     * 正常系：trueが返る
     */
    @Test
    fun evolutionCheckTrue() {
        //テストクラス作成
        val repository = mock<BoardRepository>{
            on{findEvolutionBy(3, 3)} doReturn true
            on{findLogY()} doReturn 2
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.evolutionCheck(3,3)
        assertEquals(result,true)
    }

    /**
     * 成り判定メソッド
     * 条件：成れないマスタップ
     * 正常系：falseが返る
     */
    @Test
    fun evolutionCheck() {
        //テストクラス作成
        val repository = mock<BoardRepository>{
            on{findEvolutionBy(3, 3)} doReturn false
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.evolutionCheck(3,3)
        assertEquals(result,false)
    }

    /**
     * 強制でならないといけないか判定するメソッド
     * 条件；強制でならないといけないようにする
     * 結果：trueを返し、BoardRepositoryの成り判定メソッドが呼ばれる
     */
    @Test
    fun compulsionEvolutionCheckTrue() {
        val repository = mock<BoardRepository>{
            on{checkForcedevolution()} doReturn true
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.compulsionEvolutionCheck()
        assertEquals(result,true)
        verify(repository, times(1)).setEvolution()
    }

    /**
     * 強制でならないといけないか判定するメソッド
     * 条件；成るか選択できるようにする
     * 結果：trueを返し、BoardRepositoryの成り判定メソッドが呼ばれる
     */
    @Test
    fun compulsionEvolutionCheckFalse() {
        val repository = mock<BoardRepository>{
            on{checkForcedevolution()} doReturn false
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.compulsionEvolutionCheck()
        assertEquals(result,false)
        verify(repository, times(0)).setEvolution()
    }

    /**
     * 成るメソッド
     * 結果：BoardRepositoryの成りメソッドが呼ばれる
     */
    @Test
    fun evolutionPiece() {
        val repository = mock<BoardRepository>{}
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.evolutionPiece(true)
        verify(repository, times(1)).setEvolution()
    }

    /**
     * ゲーム終了判定メソッド
     * 条件：詰み判定を返す
     * 結果：trueを返す
     */
    @Test
    fun checkGameEndTrue1() {
        val repository = mock<BoardRepository>{
            on{findKing(anyInt())} doReturn Pair(5,5)
            on{getPiece(anyInt(), anyInt())} doReturn Piece.FU
            on{getTurn(anyInt(),anyInt())} doReturn 1
            on{getCountByHint()} doReturn 0//詰み判定
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.checkGameEnd()
        assertEquals(result,true)
    }

    /**
     * ゲーム終了判定メソッド
     * 条件：王手されているが逃げる場所がある
     * 結果：falseを返す
     */
    @Test
    fun checkGameEndFalse1() {
        val repository = mock<BoardRepository>{
            on{findKing(anyInt())} doReturn Pair(5,5)
            on{getPiece(anyInt(), anyInt())} doReturn Piece.FU
            on{getTurn(anyInt(),anyInt())} doReturn 1
            on{getCountByHint()} doReturn 3//詰み判定
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.checkGameEnd()
        assertEquals(result,false)
    }

    /**
     * ゲーム終了判定メソッド
     * 条件：王手されていない
     * 結果：falseを返す
     */
    @Test
    fun checkGameEndFalse2() {
        val repository = mock<BoardRepository>{
            on{findKing(anyInt())} doReturn Pair(5,5)
            on{getPiece(anyInt(), anyInt())} doReturn Piece.None
            on{getTurn(anyInt(),anyInt())} doReturn 1
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.checkGameEnd()
        assertEquals(result,false)
    }

    /**
     * キャンセル関数
     * 正常系：ヒントリセットメソッドが呼ばれる
     */
    @Test
    fun cancel() {
        //テストクラス作成
        val repository = mock<BoardRepository>{}
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        useCase.cancel()
        verify(repository, times(1)).resetHint()
    }

    /**
     * 与えられたマスの情報を返す関数
     * 条件：x:5,y:5を与える
     * 結果：取ってきたcellのじょうほうをそのまま返す
     */
    @Test
    fun getCellInformation() {
        //テストクラス作成
        val cell = Cell()
        cell.piece = Piece.FU
        cell.turn = 1
        cell.hint = false
        val repository = mock<BoardRepository>{
            on{ getCellInformation(5,5) } doReturn cell
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.getCellInformation(5,5)
        assertEquals(result, Triple(Piece.FU.nameJP, 1,false))
    }

    /**
     * 指定したマスの手番情報を返す関数
     * 条件：自分の駒あり、ヒントなしのマスを検索する
     * 結果；手番＝自分、が返る
     */
    @Test
    fun getCellTrunBLACKNoHint() {
        //テストクラス作成
        val cell = Cell()
        cell.piece = Piece.FU
        cell.turn = 1
        cell.hint = false
        val repository = mock<BoardRepository>{
            on{ getCellInformation(5,5) } doReturn cell
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.getCellTrun(5,5)
        assertEquals(result, 1)
    }

    /**
     * 指定したマスの手番情報を返す関数
     * 条件：自分の駒あり、ヒントなしのマスを検索する
     * 結果；手番＝ヒント、が返る
     */
    @Test
    fun getCellTrunBLACKAndHint() {
        //テストクラス作成
        val cell = Cell()
        cell.piece = Piece.FU
        cell.turn = 1
        cell.hint = true
        val repository = mock<BoardRepository>{
            on{ getCellInformation(5,5) } doReturn cell
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.getCellTrun(5,5)
        assertEquals(result, 3)
    }

    /**
     * 指定したマスの手番情報を返す関数
     * 条件：自分の駒あり、ヒントなしのマスを検索する
     * 結果；手番＝自分、が返る
     */
    @Test
    fun getCellTrunWHITENoHint() {
        //テストクラス作成
        val cell = Cell()
        cell.piece = Piece.FU
        cell.turn = 2
        cell.hint = false
        val repository = mock<BoardRepository>{
            on{ getCellInformation(5,5) } doReturn cell
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.getCellTrun(5,5)
        assertEquals(result, 2)
    }

    /**
     * 指定したマスの手番情報を返す関数
     * 条件：相手の駒あり、ヒントなしのマスを検索する
     * 結果；手番＝ヒント、が返る
     */
    @Test
    fun getCellTrunWHITEAndHint() {
        //テストクラス作成
        val cell = Cell()
        cell.piece = Piece.FU
        cell.turn = 2
        cell.hint = true
        val repository = mock<BoardRepository>{
            on{ getCellInformation(5,5) } doReturn cell
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.getCellTrun(5,5)
        assertEquals(result, 3)
    }
    /**
     * 指定したマスの手番情報を返す関数
     * 条件：駒なし、ヒントなしのマスを検索する
     * 結果；手番＝なし、が返る
     */
    @Test
    fun getCellTurnNoneNoHint() {
        //テストクラス作成
        val cell = Cell()
        cell.piece = Piece.None
        cell.turn = 0
        cell.hint = false
        val repository = mock<BoardRepository>{
            on{ getCellInformation(5,5) } doReturn cell
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.getCellTrun(5,5)
        assertEquals(result, 4)
    }

    /**
     * 指定したマスの手番情報を返す関数
     * 条件：駒なし、ヒントなしのマスを検索する
     * 結果；手番＝ヒント、が返る
     */
    @Test
    fun getCellTurnNoneAndHint() {
        //テストクラス作成
        val cell = Cell()
        cell.piece = Piece.None
        cell.turn = 0
        cell.hint = true
        val repository = mock<BoardRepository>{
            on{ getCellInformation(5,5) } doReturn cell
        }
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.getCellTrun(5,5)
        assertEquals(result, 3)
    }

    //手持ちの駒取得
    @Test
    fun getPieceHand() {
    }

    //手番取得
    @Test
    fun getTurn() {
        val repository = mock<BoardRepository>{}
        val useCase = SyogiLogicUseCaseImp(repository)
        //実行
        val result = useCase.getTurn()
        assertEquals(result,1)
    }
}