package Run;
import Remove.BallRemover;
import Remove.BlockRemover;
import Score.ScoreIndicator;
import Score.ScoreTrackingListener;
import biuoop.DrawSurface;
import biuoop.Sleeper;
import java.awt.Color;
import Sprites.Sprite;
import Sprites.SpriteCollection;
import Collision.Collidable;
import Collision.GameEnvironment;
import Geo.Rectangle;
import Geo.Point;
import Geo.Velocity;
import Interactable.Block;
import Interactable.Ball;
import Interactable.Paddle;


/**
 * Game Class.
 */
public class Game {

    private SpriteCollection sprites;
    private GameEnvironment environment;

    private BlockRemover blockRemover;

    private BallRemover ballRemover;

    private ScoreTrackingListener score;

    /**
     * Constructor.
     */
    public Game() {
        this.sprites = new SpriteCollection();
        this.environment = new GameEnvironment();
        this.blockRemover = new BlockRemover(this, new Counter());
        this.ballRemover = new BallRemover(this, new Counter());
        this.score = new ScoreTrackingListener(new Counter());
    }

    /**
     * Add collidable to the environment.
     * @param c new collidable
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Remove collidable from environment.
     * @param c collidable
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Add collidable but to the start of arryList (only for paddle and logic of movement).
     * @param c collidable
     */
    public void addToStartCollidable(Collidable c) {
        this.environment.addToStartCollidable(c);
    }

    /**
     * Add sprite to the environment.
     * @param s new sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Add sprite to the (start) environment.
     * @param s new sprite
     */
    public void addToStartSprite(Sprite s) {
        this.sprites.addToStartSprite(s);
    }

    /**
     * Remove sprites from environment.
     * @param s sprite
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Getter of sprites.
     * @return sprites
     */
    public SpriteCollection getSprites() {
        return this.sprites;
    }

    /**
     * Getter of ScoreTracker.
     * @return Score tracker
     */
    public ScoreTrackingListener getSTL() {
        return this.score;
    }

    /**
     * Getter of the environment.
     * @return env
     */
    public GameEnvironment getEnvironment() {
        return this.environment;
    }

    /**
     * Getter of Ball remover.
     * @return ballremover
     */
    public BallRemover getBallRemover() {
        return this.ballRemover;
    }

    /**
     * Initialize a new game: create the Blocks, 2 Balls and Paddle
     * and add them to the game.
     */
    public void initialize() {

        //Ball 1
        Ball ball = new Ball(400, 400, 7, Color.WHITE);
        ball.setGameEnviroment(this.environment);
        ball.setVelocity(Velocity.fromAngleAndSpeed(46, 5));
        ball.addToGame(this);
        ball.addHitListener(ballRemover);
        this.ballRemover.getRemainingBalls().increase();

        //Ball 2
        Ball ball2 = new Ball(500, 400, 5, Color.WHITE);
        ball2.setGameEnviroment(this.environment);
        ball2.setVelocity(Velocity.fromAngleAndSpeed(-45, 4));
        ball2.addToGame(this);
        ball2.addHitListener(ballRemover);
        this.ballRemover.getRemainingBalls().increase();

        //Score
        ScoreIndicator scoreIndicator = new ScoreIndicator(this.score.getCounter());
        scoreIndicator.addToGame(this);

        //Paddle
        Paddle paddle = new Paddle(this.environment);
        paddle.addToGame(this);

        //Blocks
        Color[] colors = {Color.getHSBColor(0.12f, 0.15f, 1.0f), Color.getHSBColor(0.10f, 0.25f, 0.92f),
                Color.getHSBColor(0.09f, 0.45f, 0.85f), Color.getHSBColor(0.08f, 0.55f, 0.75f),
                Color.getHSBColor(0.07f, 0.65f, 0.6f), Color.getHSBColor(0.12f, 0.3f, 0.95f)};
        for (int i = 0; i < 6; ++i) {
            for (int j = 1; j < 13 - i; ++j) {
                double x = this.environment.getGui().getDrawSurface().getWidth() - (j * 50);
                double y =  150 + (i * 20);
                Block block = new Block(new Rectangle(new Point(x, y), 50, 20), colors[i]);
                block.addToGame(this);
                block.addHitListener(blockRemover);
                block.addHitListener(score);
                blockRemover.getRemainingBlocks().increase();
            }
        }

        Block deathBlockS = new Block(new Rectangle(new Point(0, 700), 1000, 50), Color.black);
        deathBlockS.turnDeathZone();
        deathBlockS.addHitListener(ballRemover);
        deathBlockS.addToGame(this);
        Block deathBlockE = new Block(new Rectangle(new Point(900, 0), 50, 1000), Color.black);
        deathBlockE.turnDeathZone();
        deathBlockE.addHitListener(ballRemover);
        deathBlockE.addToGame(this);
        Block deathBlockN = new Block(new Rectangle(new Point(0, -100), 1000, 50), Color.black);
        deathBlockN.turnDeathZone();
        deathBlockN.addHitListener(ballRemover);
        deathBlockN.addToGame(this);
        Block deathBlockW = new Block(new Rectangle(new Point(-100, 0), 50, 1000), Color.black);
        deathBlockW.turnDeathZone();
        deathBlockW.addHitListener(ballRemover);
        deathBlockW.addToGame(this);
    }

    /**
     * Run the game -- start the animation loop.
     */
    public void run() {
        int framesPerSecond = 60;
        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (true) {
            Sleeper sleeper = new Sleeper();
            long startTime = System.currentTimeMillis(); // timing

            DrawSurface d = this.environment.getGui().getDrawSurface();
            // Set background color
            d.setColor(Color.getHSBColor(0.07f, 0.7f, 0.15f));
            d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
            this.sprites.drawAllOn(d);
            this.environment.getGui().show(d);
            this.sprites.notifyAllTimePassed();

            // timing
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                sleeper.sleepFor(milliSecondLeftToSleep);
            }

            if (blockRemover.getRemainingBlocks().getValue() <= 0) { //win
                try {
                    //this.sprites.drawAllOn(d); //step fwrd to frame with no blocks
                    this.score.bonusScore(100); //win bonus
                    this.score.bonusScore(this.ballRemover.getRemainingBalls().getValue()); //bonus for every ball
                    String s = "You Win!\nYour score is: " + this.score.getCounter().getValue();
                    System.out.println(s);
                    this.environment.getGui().getDialogManager().showInformationDialog("Winner", s);
                    return;
                } finally {
                    this.environment.getGui().close();
                }
            }

            if (ballRemover.getRemainingBalls().getValue() <= 0) { //loss
                try {
                    String s = "Game Over.\nYour score is: " + this.score.getCounter().getValue();
                    System.out.println(s);
                    this.environment.getGui().getDialogManager().showInformationDialog("Loser", s);
                    return;
                } finally {
                    this.environment.getGui().close();
                }
            }

        }
    }

}