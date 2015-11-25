package bayaba.game.basic;

import android.content.Context;

import javax.microedition.khronos.opengles.GL10;

import bayaba.engine.lib.GameInfo;

public class GameMain
{
	public GL10 mGL = null; // OpenGL 객체
	public Context MainContext;
//	public Random MyRand = new Random(); // 랜덤 발생기
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	
	public GameMain( Context context, GameInfo info ) // 클래스 생성자 (메인 액티비티에서 호출)
	{
		MainContext = context; // 메인 컨텍스트를 변수에 보관한다.
		gInfo = info; // 메인 액티비티에서 생성된 클래스를 가져온다.
	}

	public void LoadGameData() // SurfaceClass에서 OpenGL이 초기화되면 최초로 호출되는 함수
	{
		// 게임 데이터를 로드합니다.
	}
	
	public void PushButton( boolean push ) // OpenGL 화면에 터치가 발생하면 GLView에서 호출된다.
	{
		// 터치를 처리합니다.
	}
	
	public void DoGame() // 1/60초에 한번씩 SurfaceClass에서 호출된다. 게임의 코어 부분을 넣는다.
	{
		synchronized ( mGL )
		{
			// 게임의 코어 부분을 코딩합니다.
		}
	}
}
