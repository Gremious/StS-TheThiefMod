   package thiefmod.actions.common;
   
   import com.megacrit.cardcrawl.actions.utility.ShakeScreenAction;
   import com.megacrit.cardcrawl.characters.AbstractPlayer;
   import com.megacrit.cardcrawl.core.AbstractCreature;
   import com.megacrit.cardcrawl.core.CardCrawlGame;
   import com.megacrit.cardcrawl.core.Settings;
   import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
   import com.megacrit.cardcrawl.localization.UIStrings;

   import static com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.MED;
   import static com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.HIGH;

   public class GainGoldAction extends com.megacrit.cardcrawl.actions.AbstractGameAction
   {
		private int goldAmount;
   
		public GainGoldAction(AbstractCreature target, AbstractCreature source, int goldAmount) {
			this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.SPECIAL;
			this.duration = Settings.ACTION_DUR_XFAST;
			this.target = target;
			this.source = source;
			this.goldAmount = goldAmount;
		}
     
		public void update()
		{
			com.megacrit.cardcrawl.core.CardCrawlGame.sound.play("GOLD_JINGLE");

			if (this.target.gold < -this.goldAmount) {
				this.goldAmount = -this.target.gold;
			}
			this.target.gold += this.goldAmount;
			this.isDone = true;
		}
   }