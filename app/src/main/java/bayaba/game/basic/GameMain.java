package bayaba.game.basic;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import bayaba.engine.lib.ButtonObject;
import bayaba.engine.lib.ButtonType;
import bayaba.engine.lib.Font;
import bayaba.engine.lib.GameInfo;
import bayaba.engine.lib.GameObject;
import bayaba.engine.lib.Sprite;

public class GameMain
{
	public GL10 mGL = null; // OpenGL 객체
	public Context MainContext;
	public Random MyRand = new Random(); // 랜덤 발생기
	public GameInfo gInfo; // 게임 환경 설정용 클래스 : MainActivity에 선언된 것을 전달 받는다.
	public float TouchX, TouchY;
	public char viewmode = 'M';

	Font font = new Font();

	Sprite MainbgSpr = new Sprite();
	Sprite MainbuttonSpr = new Sprite();

	Sprite LifeSpr = new Sprite();
	Sprite UnitButton = new Sprite();

	Sprite knight = new Sprite();

	ArrayList<GameObject> User = new ArrayList<GameObject>();
	ArrayList<GameObject> Partner = new ArrayList<GameObject>();

	ArrayList<ButtonObject>Lifebar = new ArrayList<ButtonObject>();

	ArrayList<ButtonObject> UserDeck = new ArrayList<ButtonObject>();
	ArrayList<ButtonObject> PartnerDeck = new ArrayList<ButtonObject>();

	ArrayList<ButtonObject> MainButton = new ArrayList<ButtonObject>();
	ArrayList<ButtonObject> StageButton = new ArrayList<ButtonObject>();

	ArrayList<Sprite> StagebgSpr = new ArrayList<Sprite>();
	ArrayList<Sprite> SpellsSpr = new ArrayList<Sprite>();

	ArrayList<Sprite> Castle = new ArrayList<Sprite>();

	ArrayList<GameObject> Spells = new ArrayList<GameObject>();



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
			MainButton.get(i).trans = 0.75f;
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

		if(viewtimer == 100){
			viewtimer = 0;
			seq = (int)(Math.round(Math.random() * (StagebgSpr.size() - 1)));
		}

		StagebgSpr.get(seq).PutAni(gInfo, 240, 400, 0, 0);

		for(int i = 0; i < UserDeck.size(); i++){
			UnitButton.PutAni(gInfo, 30 + (i * 30), 450, 0, 0);
		}

		// Button Check Event
		for(int i = 0; i < StageButton.size(); i++){

			if ( StageButton.get(i).type == ButtonType.TYPE_ONE_CLICK ) // 버튼 타입인지 체크한다.
			{
				if ( StageButton.get(i).click == ButtonType.STATE_CLK_BUTTON ) // 버튼이 1회 눌렸는지 체크한다. 눌렀다가 떼졌을때 STATE_CLK_BUTTON이 된다.
				{
					StageButton.get(i).ResetButton(); // 버튼 상태를 리셋해서 STATE_CLK_NORMAL로 변경한다. 다시 버튼을 누를 수 있는 상태가 된다.

					switch (i) {
						case 0:
							HeroAction('L', User.get(0));
							break;
						case 1:
							Shuffle(User.get(0));
						default:
							break;
					}
				}
			}
			StageButton.get(i).trans = 0.75f;
			StageButton.get(i).DrawSprite(mGL, 0, gInfo, font);
		}

		// UserDeck
		for(int i = 0; i < UserDeck.size(); i++){

			if ( UserDeck.get(i).type == ButtonType.TYPE_ONE_CLICK ) // 버튼 타입인지 체크한다.
			{
				if ( UserDeck.get(i).click == ButtonType.STATE_CLK_BUTTON ) // 버튼이 1회 눌렸는지 체크한다. 눌렀다가 떼졌을때 STATE_CLK_BUTTON이 된다.
				{
					UserDeck.get(i).ResetButton(); // 버튼 상태를 리셋해서 STATE_CLK_NORMAL로 변경한다. 다시 버튼을 누를 수 있는 상태가 된다.

					if(UserDeck.get(i).pattern == Castle.get(0)){
						MakeObject("pikeman", User.get(0));
					}

					if(UserDeck.get(i).pattern == Castle.get(1)){
						MakeObject("halberdier", User.get(0));
					}

					if(UserDeck.get(i).pattern == Castle.get(2)){
						MakeObject("archer", User.get(0));
					}

					UserDeck.get(i).dead = true;
				}
			}
			UserDeck.get(i).x = 30 + (i * 30);
			UserDeck.get(i).DrawSprite(mGL, 0, gInfo, font);
		}

		for(int i = 0; i < User.size(); i++){
			User.get(i).DrawSprite(gInfo);
			User.get(i).AddFrameLoop(0.2f);
		}

		for(int i = 0; i < Partner.size(); i++){
			Partner.get(i).DrawSprite(gInfo);
			Partner.get(i).AddFrameLoop(0.2f);
		}

		for(int i = 0; i < Spells.size(); i++){
			Spells.get(i).DrawSprite(gInfo);
		}


		PartnerAction();

		MotionChange();
		MoveObject();
		CrashCheck();
		DeleteObject();
	}

	public void HeroAction(char Magic, GameObject Requester){

		switch (Magic){
			case 'L':

				if(Requester == User.get(0)){
					GameObject Lightning = new GameObject();
					Lightning.SetObject(SpellsSpr.get(0), 0, 0, User.get(0).x, User.get(0).y, 0, 0);
					Lightning.damage = 5;
					Lightning.speed = 5;
					Lightning.type = 1;
					User.get(0).state = 1;
					Spells.add(Lightning);
					Log.d("라이트닝 담기 성공", String.valueOf(Spells.size()));
					break;
				}else if(Requester == Partner.get(0)){
					GameObject Lightning = new GameObject();
					Lightning.SetObject(SpellsSpr.get(0), 0, 0, Partner.get(0).x, Partner.get(0).y, 0, 0);
					Lightning.damage = 5;
					Lightning.speed = -5;
					Lightning.type = 2;
					Partner.get(0).state = 1;
					Spells.add(Lightning);
					Log.d("라이트닝 담기 성공", String.valueOf(Spells.size()));
					break;
				}

			default:
				break;
		}

	}

	public void UnitAction(char Action, GameObject Requester){

		switch (Action){
			case 'A':
				GameObject Arrow = new GameObject();
				if(Requester.type == 1){
					Arrow.SetObject(Castle.get(2), 0, 0, Requester.x, Requester.y, 2, 0);
					Arrow.speed = 3;
					Arrow.type = 1;
					Arrow.damage = Requester.damage;
					Spells.add(Arrow);
					Requester.state = 1;
				}else if(Requester.type == 2){
					Arrow.SetObject(Castle.get(2), 0, 0, Requester.x, Requester.y, 2, 0);
					Arrow.speed = -3;
					Arrow.type = 2;
					Arrow.damage = Requester.damage;
					Arrow.flip = true;
					Spells.add(Arrow);
					Requester.state = 1;
				}
				break;
			default:
				break;
		}

	}

	public void PartnerAction(){
		if(MyRand.nextInt(500) == 0){
			HeroAction('L', Partner.get(0));
		}

		if(MyRand.nextInt(1000) == 0) {
			MakeObject("pikeman", Partner.get(0));
		}

		if(MyRand.nextInt(1000) == 0) {
			MakeObject("halberdier", Partner.get(0));
		}

		if(MyRand.nextInt(1000) == 0) {
			MakeObject("archer", Partner.get(0));
		}
	}

	public void MakeLifeBar(){
		ButtonObject Lifetemp = new ButtonObject();
		Lifetemp.SetButton(LifeSpr, ButtonType.TYPE_PROGRESS, 0, 0, 0, 0);
		Lifebar.add(Lifetemp);
	}

	public void MotionChange(){
		if(User.size() > 1) Log.d("archer frame", String.valueOf(User.get(1).frame));

		for(int i = 1; i < User.size(); i++){

			if(User.get(i).pattern == Castle.get(2) && User.get(i).state == 1){
				User.get(i).motion = 0;
				User.get(i).frame = 0;
				User.get(i).state = 0;
			}

			if(User.get(i).pattern == Castle.get(2) && User.get(i).motion == 0 && User.get(i).frame > 5.9f){
				User.get(i).motion = 1;
				User.get(i).frame = 0;
			}

		}

		for(int i = 1; i < Partner.size(); i++){

			if(Partner.get(i).pattern == Castle.get(2) && Partner.get(i).state == 1){
				Partner.get(i).motion = 0;
				Partner.get(i).frame = 0;
				Partner.get(i).state = 0;
			}

			if(Partner.get(i).pattern == Castle.get(2) && Partner.get(i).motion == 0 && Partner.get(i).frame > 5.9f){
				Partner.get(i).motion = 1;
				Partner.get(i).frame = 0;
			}

		}

		if(User.get(0).state == 1){
			User.get(0).motion = 1;
			User.get(0).frame = 0;
			User.get(0).state = 0;
		}

		if(User.get(0).motion == 1 && User.get(0).frame > 8.9f){
			User.get(0).motion = 0;
			User.get(0).frame = 0;
		}

		if(Partner.get(0).state == 1){
			Partner.get(0).motion = 1;
			Partner.get(0).frame = 0;
			Partner.get(0).state = 0;
		}

		if(Partner.get(0).motion == 1 && Partner.get(0).frame > 8.9f){
			Partner.get(0).motion = 0;
			Partner.get(0).frame = 0;
		}

	}

	public void MoveObject(){

		for(int i = 0; i < User.size(); i++){

			if(User.get(i).subfr > 0) User.get(i).subfr = User.get(i).subfr * 0.95f;
			User.get(i).x += (User.get(i).speed - User.get(i).subfr);

			if(User.get(i).jump == 'Y' && User.get(i).delay <= 0){
				if(User.get(i).pattern == Castle.get(2)){
					UnitAction('A', User.get(i));
					User.get(i).delay = 300;
				}
			}

			if(User.get(i).jump == 'Y') User.get(i).delay--;

		}

		for(int i = 0; i < Partner.size(); i++){

			if(Partner.get(i).subfr > 0) Partner.get(i).subfr = Partner.get(i).subfr * 0.95f;
			Partner.get(i).x += (Partner.get(i).speed + Partner.get(i).subfr);

			if(Partner.get(i).jump == 'Y' && Partner.get(i).delay <= 0){
				if(Partner.get(i).pattern == Castle.get(2)){
					UnitAction('A', Partner.get(i));
					Partner.get(i).delay = 300;
				}
			}

			if(Partner.get(i).jump == 'Y') Partner.get(i).delay--;

		}

		for(int i = 0; i < Spells.size(); i++){
			Spells.get(i).x += Spells.get(i).speed;
		}

	}

	public void CrashCheck(){

		for(int i = 0; i < Spells.size(); i++){
			if(Spells.get(i).type == 1){
				for(int j = 0; j < Partner.size(); j++) {
					if (gInfo.CrashCheck(Spells.get(i), Partner.get(j), 0, 0)){
						Partner.get(j).energy -= Spells.get(i).damage;

						if(Partner.get(j).energy <= 0) Partner.get(j).dead = true;
						Spells.get(i).dead = true;
					}
				}
			}else if(Spells.get(i).type == 2){
				for(int j = 0; j < User.size(); j++) {
					if (gInfo.CrashCheck(Spells.get(i), User.get(j), 0, 0)){
						User.get(j).energy -= Spells.get(i).damage;

						if(User.get(j).energy <= 0) User.get(j).dead = true;
						Spells.get(i).dead = true;
					}
				}
			}
		}


		for(int i = 0; i < User.size(); i++){
			for(int j = 0; j < Partner.size(); j++){
				if(gInfo.CrashCheck(User.get(i), Partner.get(j), 0, 0)){
					User.get(i).energy -= Partner.get(j).damage;
					Partner.get(j).energy -= User.get(i).damage;

					if(i != 0 && User.get(i).jump != 'Y') User.get(i).subfr = 3;
					if(j != 0 && Partner.get(j).jump != 'Y') Partner.get(j).subfr = 3;

					if(User.get(i).energy <= 0) User.get(i).dead = true;
					if(Partner.get(j).energy <= 0) Partner.get(j).dead = true;
				}
			}
		}
	}

	public void Shuffle(GameObject Object){
		ButtonObject Buttontemp = new ButtonObject();

		if(Object == User.get(0)){
			Buttontemp.SetButton(Castle.get((int) (Math.round(Math.random() * (Castle.size() - 1)))), ButtonType.TYPE_ONE_CLICK, 0, 30 + (UserDeck.size() * 30), 450, 0);
			if(Buttontemp.pattern == Castle.get(0)) Buttontemp.motion = 2;
			if(Buttontemp.pattern == Castle.get(1)) Buttontemp.motion = 2;
			if(Buttontemp.pattern == Castle.get(2)) Buttontemp.motion = 3;
			UserDeck.add(Buttontemp);
		}

	}

	public void MakeObject(String ObjectName, GameObject Requester){

		if(ObjectName == "pikeman"){
			GameObject pikeman = new GameObject();
			pikeman.SetObject(Castle.get(0), 0, 0, Requester.x, Requester.y, 0, 0);

			pikeman.damage = 1;
			pikeman.energy = 3;

			if(Requester == User.get(0)){
				pikeman.speed = 0.5f;
				pikeman.type = 1;
				User.add(pikeman);
			}
			else{
				pikeman.speed = -0.5f;
				pikeman.flip = true;
				pikeman.type = 2;
				Partner.add(pikeman);
			}
		}

		if(ObjectName == "halberdier"){
			GameObject halberdier = new GameObject();
			halberdier.SetObject(Castle.get(1), 0, 0, Requester.x, Requester.y, 0, 0);

			halberdier.damage = 1;
			halberdier.energy = 5;

			if(Requester == User.get(0)){
				halberdier.speed = 0.5f;
				halberdier.type = 1;
				User.add(halberdier);
			}
			else{
				halberdier.speed = -0.5f;
				halberdier.type = 2;
				halberdier.flip = true;
				Partner.add(halberdier);
			}
		}

		if(ObjectName == "archer"){
			GameObject archer = new GameObject();
			archer.SetObject(Castle.get(2), 0, 0, Requester.x, Requester.y, 1, 0);

			archer.damage = 1;
			archer.energy = 3;
			archer.jump = 'Y';
			int addx = 0;

			if(Requester == User.get(0)){
				for(int i = 0; i < User.size(); i ++) if(User.get(i).pattern == Castle.get(2)) addx += 15;
				archer.x = (User.get(0).x + 15 + addx);
				archer.type = 1;
				User.add(archer);
			}
			else{
				for(int i = 0; i < Partner.size(); i ++) if(Partner.get(i).pattern == Castle.get(2)) addx += 15;
				archer.x = ((Partner.get(0).x - 15) - addx);
				archer.flip = true;
				archer.type = 2;
				Partner.add(archer);
			}
		}

	}

	public void DeleteObject(){
		for(int i = 1; i < User.size(); i++){
			if(User.get(i).dead == true) User.remove(i);
		}

		for(int i = 0; i < UserDeck.size(); i++){
			if(UserDeck.get(i).dead == true) UserDeck.remove(i);
		}

		for(int i = 1; i < Partner.size(); i++){
			if(Partner.get(i).dead == true) Partner.remove(i);
		}

		for(int i = 0; i < PartnerDeck.size(); i++){
			if(PartnerDeck.get(i).dead == true) PartnerDeck.remove(i);
		}

		for(int i = 0; i < Spells.size(); i++){
			if(Spells.get(i).dead == true) Spells.remove(i);
		}

		if(User.get(0).dead) viewmode = 'L';
		if(Partner.get(0).dead) viewmode = 'W';
	}

	public GameMain( Context context, GameInfo info ) // 클래스 생성자 (메인 액티비티에서 호출)
	{
		MainContext = context; // 메인 컨텍스트를 변수에 보관한다.
		gInfo = info; // 메인 액티비티에서 생성된 클래스를 가져온다.
	}

	public void LoadGameData() // SurfaceClass에서 OpenGL이 초기화되면 최초로 호출되는 함수
	{
		User.clear();
		Partner.clear();
		Spells.clear();

		// 게임 데이터를 로드합니다.
		MainbgSpr.LoadSprite(mGL, MainContext, "main/Mainbg.spr");
		MainbuttonSpr.LoadSprite(mGL, MainContext, "button/Mainbutton.spr");

		LifeSpr.LoadSprite(mGL, MainContext, "common/Lifebar.spr");

		Sprite Spritetemp;
		GameObject GameObjecttemp;
		ButtonObject Buttontemp;

		// battlebg
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

		// spells
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "spells/Lightning.spr");
		SpellsSpr.add(Spritetemp);


		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "castle/pikeman.spr");
		Castle.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "castle/halberdier.spr");
		Castle.add(Spritetemp);
		Spritetemp = new Sprite();
		Spritetemp.LoadSprite(mGL, MainContext, "castle/archer.spr");
		Castle.add(Spritetemp);


		knight.LoadSprite(mGL, MainContext, "hero/knight.spr");

		UnitButton.LoadSprite(mGL, MainContext, "button/Unitbutton.spr");

		GameObjecttemp = new GameObject();
		GameObjecttemp.SetObject(knight, 0, 0, 20, 300, 0, 0);
		GameObjecttemp.energy = 1000;
		GameObjecttemp.damage = 1;
		User.add(GameObjecttemp);

		GameObjecttemp = new GameObject();
		GameObjecttemp.SetObject(knight, 0, 0, 460, 300, 0, 0);
		GameObjecttemp.energy = 1000;
		GameObjecttemp.damage = 1;
		GameObjecttemp.flip = true;
		Partner.add(GameObjecttemp);


		Buttontemp = new ButtonObject();
		Buttontemp.SetButton(MainbuttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 240, 450, 0);
		MainButton.add(Buttontemp);

		Buttontemp = new ButtonObject();
		Buttontemp.SetButton(MainbuttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 240, 525, 0);
		MainButton.add(Buttontemp);

		Buttontemp = new ButtonObject();
		Buttontemp.SetButton(MainbuttonSpr, ButtonType.TYPE_ONE_CLICK, 0, 240, 600, 0);
		MainButton.add(Buttontemp);


		Buttontemp = new ButtonObject();
		Buttontemp.SetButton(SpellsSpr.get(0), ButtonType.TYPE_ONE_CLICK, 0, 30, 30, 0);
		StageButton.add(Buttontemp);

		Buttontemp = new ButtonObject();
		Buttontemp.SetButton(SpellsSpr.get(0), ButtonType.TYPE_ONE_CLICK, 0, 150, 30, 0);
		StageButton.add(Buttontemp);

	}
	
	public void PushButton( boolean push ) // OpenGL 화면에 터치가 발생하면 GLView에서 호출된다.
	{
		// 터치를 처리합니다.
		for ( int i = 0; i < MainButton.size(); i++ ) MainButton.get(i).CheckButton( gInfo, push, TouchX, TouchY );
		for ( int i = 0; i < StageButton.size(); i++ ) StageButton.get(i).CheckButton( gInfo, push, TouchX, TouchY );
		for ( int i = 0; i < UserDeck.size(); i++ ) UserDeck.get(i).CheckButton( gInfo, push, TouchX, TouchY );
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
				case 'W':
					Maininit();
					break;
				case 'L':
					Maininit();
					break;
				default:
					Maininit();
					break;
			}

		}
	}
}
