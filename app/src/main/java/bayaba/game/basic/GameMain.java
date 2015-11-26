package bayaba.game.basic;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
import bayaba.engine.lib.Font;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.Sprite;

public class GameMain
{
	public GL10 mGL = null; // OpenGL 객체
	public Context MainContext;
//	public Random MyRand = new Random(); // 랜덤 발생기
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	public char viewmode = 'M';


	Font font = new Font();

	Sprite MainbgSpr = new Sprite();
	Sprite MainbuttonSpr = new Sprite();

	ArrayList<ButtonObject> MainButton = new ArrayList<ButtonObject>();


	ArrayList<Sprite> StagebgSpr = new ArrayList<Sprite>();






	public void Maininit(){

		MainbgSpr.PutAni(gInfo, 240, 400, 0, 0);

		// Button Check Event
		for(int i = 0; i < MainButton.size(); i++){

			if ( MainButton.get(i).type == ButtonType.TYPE_ONE_CLICK ) // 버튼 타입인지 체크한다.
			{
				if ( MainButton.get(i).click == ButtonType.STATE_CLK_BUTTON ) // 버튼이 1회 눌렸는지 체크한다. 눌렀다가 떼졌을때 STATE_CLK_BUTTON이 된다.
				{
					MainButton.get(i).ResetButton(); // 버튼 상태를 리셋해서 STATE_CLK_NORMAL로 변경한다. 다시 버튼을 누를 수 있는 상태가 된다.

					switch (i) {
						case 0:
							viewmode = 'S';
							return;
						case 1:
							viewmode = 'U';
							return;
						case 2:
							viewmode = 'O';
							return;
					}
				}
			}

			// Button Draw
			MainButton.get(i).DrawSprite(mGL, 0, gInfo, font);
		}

		font.BeginFont(gInfo);
		font.LoadFont(MainContext, "font/digiffiti.ttf");
		font.DrawFont(mGL, 20, 5, 80, "Heroes  Shuffle");

		font.DrawFont(mGL, 150, 425, 35, "GAME START");
		font.DrawFont(mGL, 120, 500, 35, "UPGRADE CENTER");
		font.DrawFont(mGL, 145, 575, 35, "OTHER GAMES");

		font.DrawFont(mGL, 250, 750, 30, "Make by Ssarubi v1.0");
		font.EndFont(gInfo);

	}

	int viewtimer = 0;
	int seq = 0;

	public void Stageinit(){
		if(viewmode == 'S') viewtimer ++;

		if(viewtimer == 120){
			viewtimer = 0;
			seq = (int)(Math.round(Math.random() * 15));
		}

		StagebgSpr.get(seq).PutAni(gInfo, 240, 400, 0, 0);
		Log.d("viewtimer", String.valueOf(viewtimer));


	}


	public GameMain( Context context, GameInfo info ) // 클래스 생성자 (메인 액티비티에서 호출)
	{
		MainContext = context; // 메인 컨텍스트를 변수에 보관한다.
		gInfo = info; // 메인 액티비티에서 생성된 클래스를 가져온다.
	}

	public void LoadGameData() // SurfaceClass에서 OpenGL이 초기화되면 최초로 호출되는 함수
	{
		// 게임 데이터를 로드합니다.
		MainbgSpr.LoadSprite(mGL, MainContext, "main/Mainbg.spr");
		MainbuttonSpr.LoadSprite(mGL, MainContext, "button/Mainbutton.spr");

		Sprite Spritetemp;

		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkSub.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkSnTr.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkSnMt.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkRK.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkRgh.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkLP.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkLava.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkHG.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkGrTr.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkGrMt.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkDrTr.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkDrMt.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkDrDd.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkDes.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkCur.spr");
		StagebgSpr.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "battlebg/CmBkCF.spr");
		StagebgSpr.add(Spritetemp);


		ButtonObject Buttontemp;

		Buttontemp = new ButtonObject();
		Buttontemp.SetButton(MainbuttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 240, 450, 0);
		MainButton.add(Buttontemp);

		Buttontemp = new ButtonObject();
		Buttontemp.SetButton(MainbuttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 240, 525, 0);
		MainButton.add(Buttontemp);

		Buttontemp = new ButtonObject();
		Buttontemp.SetButton(MainbuttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 240, 600, 0);
		MainButton.add(Buttontemp);

	}
	
	public void PushButton( boolean push ) // OpenGL 화면에 터치가 발생하면 GLView에서 호출된다.
	{
		// 터치를 처리합니다.
		for ( int i = 0; i < MainButton.size(); i++ )MainButton.get(i).CheckButton( gInfo, push, TouchX, TouchY );
	}


	public void DoGame() // 1/60초에 한번씩 SurfaceClass에서 호출된다. 게임의 코어 부분을 넣는다.
	{
		synchronized ( mGL )
		{
			// 게임의 코어 부분을 코딩합니다.
			switch (viewmode) {
				case 'M':
					Maininit();
					break;
				case 'S':
					Stageinit();
					break;
				default:
					Maininit();
					break;
			}

		}
	}
}
