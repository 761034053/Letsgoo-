package com.lingdong.letsgoo.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.*;
import android.widget.AbsListView.OnScrollListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.lingdong.letsgoo.R;

/**
 * ListView涓嬫媺鍒锋柊鍜屽姞杞芥洿澶�p>
 * 
 * <strong>鍙樻洿璇存槑:</strong>
 * <p>榛樿濡傛灉璁剧疆浜哋nRefreshListener鎺ュ彛鍜孫nLoadMoreListener鎺ュ彛锛�br>骞朵笖涓嶄负null锛屽垯鎵撳紑杩欎袱涓姛鑳戒簡銆�
 * <p>鍓╀綑涓や釜Flag锛歮IsAutoLoadMore(鏄惁鑷姩鍔犺浇鏇村)鍜�
 * <br>mIsMoveToFirstItemAfterRefresh(涓嬫媺鍒锋柊鍚庢槸鍚︽樉绀虹涓�潯Item)
 * 
 * <p><strong>鏈夋敼杩涙剰瑙侊紝璇峰彂閫佸埌淇虹殑閭鍝垀 澶氳阿鍚勪綅灏忎紮浼翠簡锛乛_^</strong>
 * 
 * @date 2013-11-11 涓嬪崍10:09:26
 * @change JohnWatson 
 * @mail xxzhaofeng5412@gmail.com
 * @version 1.0
 */
public class CustomListView extends ListView implements OnScrollListener {

	/**  鏄剧ず鏍煎紡鍖栨棩鏈熸ā鏉�  */
	private final static String DATE_FORMAT_STR = "yyyy年M月d日 HH:mm";
	
	/**  瀹為檯鐨刾adding鐨勮窛绂讳笌鐣岄潰涓婂亸绉昏窛绂荤殑姣斾緥   */
	private final static int RATIO = 3;
	
	private final static int RELEASE_TO_REFRESH = 0;
	private final static int PULL_TO_REFRESH = 1;
	private final static int REFRESHING = 2;
	private final static int DONE = 3;
	private final static int LOADING = 4;
	
	/**  鍔犺浇涓�  */
	private final static int ENDINT_LOADING = 1;
	/**  鎵嬪姩瀹屾垚鍒锋柊   */
	private final static int ENDINT_MANUAL_LOAD_DONE = 2;
	/**  鑷姩瀹屾垚鍒锋柊   */
	private final static int ENDINT_AUTO_LOAD_DONE = 3;
	
	/**    0:RELEASE_TO_REFRESH;
	 * <p> 1:PULL_To_REFRESH;
	 * <p> 2:REFRESHING;
	 * <p> 3:DONE;
	 * <p> 4:LOADING */
	private int mHeadState;
	/**    0:瀹屾垚/绛夊緟鍒锋柊 ;
	 * <p> 1:鍔犺浇涓� */
	private int mEndState;
	
	// ================================= 鍔熻兘璁剧疆Flag ================================
	
	/**  鍙互鍔犺浇鏇村锛�  */
	private boolean mCanLoadMore = false;
	/**  鍙互涓嬫媺鍒锋柊锛�  */
	private boolean mCanRefresh = false;
	/**  鍙互鑷姩鍔犺浇鏇村鍚楋紵锛堟敞鎰忥紝鍏堝垽鏂槸鍚︽湁鍔犺浇鏇村锛屽鏋滄病鏈夛紝杩欎釜flag涔熸病鏈夋剰涔夛級   */
	private boolean mIsAutoLoadMore = true;
	/** 涓嬫媺鍒锋柊鍚庢槸鍚︽樉绀虹涓�潯Item    */
	private boolean mIsMoveToFirstItemAfterRefresh = false;
	
	public boolean isCanLoadMore() {
		return mCanLoadMore;
	}
	
	public void setCanLoadMore(boolean pCanLoadMore) {
		mCanLoadMore = pCanLoadMore;
		if(mCanLoadMore && getFooterViewsCount() == 0){
			addFooterView();
		}
	}
	
	public boolean isCanRefresh() {
		return mCanRefresh;
	}
	
	public void setCanRefresh(boolean pCanRefresh) {
		mCanRefresh = pCanRefresh;
	}
	
	public boolean isAutoLoadMore() {
		return mIsAutoLoadMore;
	}

	public void setAutoLoadMore(boolean pIsAutoLoadMore) {
		mIsAutoLoadMore = pIsAutoLoadMore;
	}
		
	public boolean isMoveToFirstItemAfterRefresh() {
		return mIsMoveToFirstItemAfterRefresh;
	}

	public void setMoveToFirstItemAfterRefresh(
			boolean pIsMoveToFirstItemAfterRefresh) {
		mIsMoveToFirstItemAfterRefresh = pIsMoveToFirstItemAfterRefresh;
	}
	
	// ============================================================================

	private LayoutInflater mInflater;

	private LinearLayout mHeadView;
	private TextView mTipsTextView;
	private TextView mLastUpdatedTextView;
	private ImageView mArrowImageView;
	private ProgressBar mProgressBar;
	
	private View mEndRootView;
	private ProgressBar mEndLoadProgressBar;
	private TextView mEndLoadTipsTextView;

	/**  headView鍔ㄧ敾   */
	private RotateAnimation mArrowAnim;
	/**  headView鍙嶈浆鍔ㄧ敾   */
	private RotateAnimation mArrowReverseAnim;
 
	/** 鐢ㄤ簬淇濊瘉startY鐨勫�鍦ㄤ竴涓畬鏁寸殑touch浜嬩欢涓彧琚褰曚竴娆�   */
	private boolean mIsRecored;

	private int mHeadViewWidth;
	private int mHeadViewHeight;

	private int mStartY;
	private boolean mIsBack;
	
	private int mFirstItemIndex;
	private int mLastItemIndex;
	private int mCount;
	private boolean mEnoughCount;
	
	private OnRefreshListener mRefreshListener;
	private OnLoadMoreListener mLoadMoreListener;

	public CustomListView(Context pContext, AttributeSet pAttrs) {
		super(pContext, pAttrs);
		init(pContext);
	}

	public CustomListView(Context pContext) {
		super(pContext);
		init(pContext);
	}

	public CustomListView(Context pContext, AttributeSet pAttrs, int pDefStyle) {
		super(pContext, pAttrs, pDefStyle);
		init(pContext);
	}

	/**
	 * 鍒濆鍖栨搷浣�
	 * @param pContext 
	 * @date 2013-11-20 涓嬪崍4:10:46
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void init(Context pContext) {
		setCacheColorHint(pContext.getResources().getColor(R.color.transparent));
		mInflater = LayoutInflater.from(pContext);

		addHeadView();
		
		setOnScrollListener(this);

		initPullImageAnimation(0);
	}

	/**
	 * 娣诲姞涓嬫媺鍒锋柊鐨凥eadView 
	 * @date 2013-11-11 涓嬪崍9:48:26
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void addHeadView() {
		mHeadView = (LinearLayout) mInflater.inflate(R.layout.head, null);

		mArrowImageView = (ImageView) mHeadView
				.findViewById(R.id.head_arrowImageView);
		mArrowImageView.setMinimumWidth(70);
		mArrowImageView.setMinimumHeight(50);
		mProgressBar = (ProgressBar) mHeadView
				.findViewById(R.id.head_progressBar);
		mTipsTextView = (TextView) mHeadView.findViewById(
				R.id.head_tipsTextView);
		mLastUpdatedTextView = (TextView) mHeadView
				.findViewById(R.id.head_lastUpdatedTextView);

		measureView(mHeadView);
		mHeadViewHeight = mHeadView.getMeasuredHeight();
		mHeadViewWidth = mHeadView.getMeasuredWidth();
		
		mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
		mHeadView.invalidate();

		Log.v("size", "width:" + mHeadViewWidth + " height:"
                + mHeadViewHeight);

		addHeaderView(mHeadView, null, false);
		
		mHeadState = DONE;
	}
	
	/**
	 * 娣诲姞鍔犺浇鏇村FootView
	 * @date 2013-11-11 涓嬪崍9:52:37
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void addFooterView() {
		mEndRootView = mInflater.inflate(R.layout.list_footer_more, null);
		mEndRootView.setVisibility(View.VISIBLE);
		mEndLoadProgressBar = (ProgressBar) mEndRootView
				.findViewById(R.id.pull_to_refresh_progress);
		mEndLoadTipsTextView = (TextView) mEndRootView.findViewById(R.id.load_more);
		mEndRootView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(mCanLoadMore){
					if(mCanRefresh){
						// 褰撳彲浠ヤ笅鎷夊埛鏂版椂锛屽鏋淔ootView娌℃湁姝ｅ湪鍔犺浇锛屽苟涓擧eadView娌℃湁姝ｅ湪鍒锋柊锛屾墠鍙互鐐瑰嚮鍔犺浇鏇村銆�
						if(mEndState != ENDINT_LOADING && mHeadState != REFRESHING){
							mEndState = ENDINT_LOADING;
							onLoadMore();
						}
					}else if(mEndState != ENDINT_LOADING){
						// 褰撲笉鑳戒笅鎷夊埛鏂版椂锛孎ootView涓嶆鍦ㄥ姞杞芥椂锛屾墠鍙互鐐瑰嚮鍔犺浇鏇村銆�
						mEndState = ENDINT_LOADING;
						onLoadMore();
					}
				}
			}
		});
		
		addFooterView(mEndRootView);
		
		if(mIsAutoLoadMore){
			mEndState = ENDINT_AUTO_LOAD_DONE;
		}else{
			mEndState = ENDINT_MANUAL_LOAD_DONE;
		}
	}

	/**
	 * 瀹炰緥鍖栦笅鎷夊埛鏂扮殑绠ご鐨勫姩鐢绘晥鏋�
	 * @param pAnimDuration 鍔ㄧ敾杩愯鏃堕暱
	 * @date 2013-11-20 涓婂崍11:53:22
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void initPullImageAnimation(final int pAnimDuration) {
		
		int _Duration;
		
		if(pAnimDuration > 0){
			_Duration = pAnimDuration;
		}else{
			_Duration = 250;
		}
//		Interpolator _Interpolator;
//		switch (pAnimType) {
//		case 0:
//			_Interpolator = new AccelerateDecelerateInterpolator();
//			break;
//		case 1:
//			_Interpolator = new AccelerateInterpolator();
//			break;
//		case 2:
//			_Interpolator = new AnticipateInterpolator();
//			break;
//		case 3:
//			_Interpolator = new AnticipateOvershootInterpolator();
//			break;
//		case 4:
//			_Interpolator = new BounceInterpolator();
//			break;
//		case 5:
//			_Interpolator = new CycleInterpolator(1f);
//			break;
//		case 6:
//			_Interpolator = new DecelerateInterpolator();
//			break;
//		case 7:
//			_Interpolator = new OvershootInterpolator();
//			break;
//		default:
//			_Interpolator = new LinearInterpolator();
//			break;
//		}
		
		Interpolator _Interpolator = new LinearInterpolator();
		
		mArrowAnim = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mArrowAnim.setInterpolator(_Interpolator);
		mArrowAnim.setDuration(_Duration);
		mArrowAnim.setFillAfter(true);

		mArrowReverseAnim = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mArrowReverseAnim.setInterpolator(_Interpolator);
		mArrowReverseAnim.setDuration(_Duration);
		mArrowReverseAnim.setFillAfter(true);
	}

	/**
	 * 娴嬮噺HeadView瀹介珮(娉ㄦ剰锛氭鏂规硶浠呴�鐢ㄤ簬LinearLayout锛岃璇昏�鑷繁娴嬭瘯楠岃瘉銆�
	 * @param pChild 
	 * @date 2013-11-20 涓嬪崍4:12:07
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void measureView(View pChild) {
		ViewGroup.LayoutParams p = pChild.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;

		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
                    MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
                    MeasureSpec.UNSPECIFIED);
		}
		pChild.measure(childWidthSpec, childHeightSpec);
	}
	
	/**
	 *涓轰簡鍒ゆ柇婊戝姩鍒癓istView搴曢儴娌�
	 */
	@Override
	public void onScroll(AbsListView pView, int pFirstVisibleItem,
			int pVisibleItemCount, int pTotalItemCount) {
		mFirstItemIndex = pFirstVisibleItem;
		mLastItemIndex = pFirstVisibleItem + pVisibleItemCount - 2;
		mCount = pTotalItemCount - 2;
		if (pTotalItemCount > pVisibleItemCount ) {
			mEnoughCount = true;
//			endingView.setVisibility(View.VISIBLE);
		} else {
			mEnoughCount = false;
		}
	}

	/**
	 *杩欎釜鏂规硶锛屽彲鑳芥湁鐐逛贡锛屽ぇ瀹跺璇诲嚑閬嶅氨鏄庣櫧浜嗐�
	 */
	@Override
	public void onScrollStateChanged(AbsListView pView, int pScrollState) {
		if(mCanLoadMore){// 瀛樺湪鍔犺浇鏇村鍔熻兘
			if (mLastItemIndex ==  mCount && pScrollState == SCROLL_STATE_IDLE) {
				//SCROLL_STATE_IDLE=0锛屾粦鍔ㄥ仠姝�
				if (mEndState != ENDINT_LOADING) {
					if(mIsAutoLoadMore){// 鑷姩鍔犺浇鏇村锛屾垜浠FootView鏄剧ず 鈥滄洿    澶氣�
						if(mCanRefresh){
							// 瀛樺湪涓嬫媺鍒锋柊骞朵笖HeadView娌℃湁姝ｅ湪鍒锋柊鏃讹紝FootView鍙互鑷姩鍔犺浇鏇村銆�
							if(mHeadState != REFRESHING){
								// FootView鏄剧ず : 鏇�   澶� ---> 鍔犺浇涓�..
								mEndState = ENDINT_LOADING;
								onLoadMore();
								changeEndViewByState();
							}
						}else{// 娌℃湁涓嬫媺鍒锋柊锛屾垜浠洿鎺ヨ繘琛屽姞杞芥洿澶氥�
							// FootView鏄剧ず : 鏇�   澶� ---> 鍔犺浇涓�..
							mEndState = ENDINT_LOADING;
							onLoadMore();
							changeEndViewByState();
						}
					}else{// 涓嶆槸鑷姩鍔犺浇鏇村锛屾垜浠FootView鏄剧ず 鈥滅偣鍑诲姞杞解�
						// FootView鏄剧ず : 鐐瑰嚮鍔犺浇  ---> 鍔犺浇涓�..
						mEndState = ENDINT_MANUAL_LOAD_DONE;
						changeEndViewByState();
					}
				}
			}
		}else if(mEndRootView != null && mEndRootView.getVisibility() == VISIBLE){
			// 绐佺劧鍏抽棴鍔犺浇鏇村鍔熻兘涔嬪悗锛屾垜浠绉婚櫎FootView銆�
			System.out.println("this.removeFooterView(endRootView);...");
			mEndRootView.setVisibility(View.GONE);
			this.removeFooterView(mEndRootView);
		}
	}

	/**
	 * 鏀瑰彉鍔犺浇鏇村鐘舵�
	 * @date 2013-11-11 涓嬪崍10:05:27
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void  changeEndViewByState() {
		if (mCanLoadMore) {
			//鍏佽鍔犺浇鏇村
			switch (mEndState) {
			case ENDINT_LOADING://鍒锋柊涓�
				
				// 鍔犺浇涓�..
				if(mEndLoadTipsTextView.getText().equals(
						R.string.p2refresh_doing_end_refresh)){
					break;
				}
				mEndLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.VISIBLE);
				break;
			case ENDINT_MANUAL_LOAD_DONE:// 鎵嬪姩鍒锋柊瀹屾垚
				
				// 鐐瑰嚮鍔犺浇
				mEndLoadTipsTextView.setText(R.string.p2refresh_end_click_load_more);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.GONE);
				
				mEndRootView.setVisibility(View.VISIBLE);
				break;
			case ENDINT_AUTO_LOAD_DONE:// 鑷姩鍒锋柊瀹屾垚
				
				// 鏇�   澶�
				mEndLoadTipsTextView.setText(R.string.p2refresh_end_load_more);
				mEndLoadTipsTextView.setVisibility(View.VISIBLE);
				mEndLoadProgressBar.setVisibility(View.GONE);
				
				mEndRootView.setVisibility(View.VISIBLE);
				break;
			default:
				// 鍘熸潵鐨勪唬鐮佹槸涓轰簡锛�褰撴墍鏈塱tem鐨勯珮搴﹀皬浜嶭istView鏈韩鐨勯珮搴︽椂锛�
				// 瑕侀殣钘忔帀FootView锛屽ぇ瀹惰嚜宸卞幓鍘熶綔鑰呯殑浠ｇ爜鍙傝�銆�
				
//				if (enoughCount) {					
//					endRootView.setVisibility(View.VISIBLE);
//				} else {
//					endRootView.setVisibility(View.GONE);
//				}
				break;
			}
		}
	}
	
	/**
	 *鍘熶綔鑰呯殑锛屾垜娌℃敼鍔紝璇疯鑰呰嚜琛屼紭鍖栥�
	 */
	public boolean onTouchEvent(MotionEvent event) {
		
		if (mCanRefresh) {
			if(mCanLoadMore && mEndState == ENDINT_LOADING){
				// 濡傛灉瀛樺湪鍔犺浇鏇村鍔熻兘锛屽苟涓斿綋鍓嶆鍦ㄥ姞杞芥洿澶氾紝榛樿涓嶅厑璁镐笅鎷夊埛鏂帮紝蹇呴』鍔犺浇瀹屾瘯鍚庢墠鑳戒娇鐢ㄣ�
				return super.onTouchEvent(event);
			}
			
			switch (event.getAction()) {
			
			case MotionEvent.ACTION_DOWN:
				if (mFirstItemIndex == 0 && !mIsRecored) {
					mIsRecored = true;
					mStartY = (int) event.getY();
				}
				break;

			case MotionEvent.ACTION_UP:

				if (mHeadState != REFRESHING && mHeadState != LOADING) {
					if (mHeadState == DONE) {
						
					}
					if (mHeadState == PULL_TO_REFRESH) {
						mHeadState = DONE;
						changeHeaderViewByState();
					}
					if (mHeadState == RELEASE_TO_REFRESH) {
						mHeadState = REFRESHING;
						changeHeaderViewByState();
						onRefresh();
					}
				}

				mIsRecored = false;
				mIsBack = false;

				break;

			case MotionEvent.ACTION_MOVE:
				int tempY = (int) event.getY();

				if (!mIsRecored && mFirstItemIndex == 0) {
					mIsRecored = true;
					mStartY = tempY;
				}

				if (mHeadState != REFRESHING && mIsRecored && mHeadState != LOADING) {

					// 淇濊瘉鍦ㄨ缃畃adding鐨勮繃绋嬩腑锛屽綋鍓嶇殑浣嶇疆涓�洿鏄湪head锛�
					// 鍚﹀垯濡傛灉褰撳垪琛ㄨ秴鍑哄睆骞曠殑璇濓紝褰撳湪涓婃帹鐨勬椂鍊欙紝鍒楄〃浼氬悓鏃惰繘琛屾粴鍔�
					// 鍙互鏉炬墜鍘诲埛鏂颁簡
					if (mHeadState == RELEASE_TO_REFRESH) {

						setSelection(0);

						// 寰�笂鎺ㄤ簡锛屾帹鍒颁簡灞忓箷瓒冲鎺╃洊head鐨勭▼搴︼紝浣嗘槸杩樻病鏈夋帹鍒板叏閮ㄦ帺鐩栫殑鍦版
						if (((tempY - mStartY) / RATIO < mHeadViewHeight)
								&& (tempY - mStartY) > 0) {
							mHeadState = PULL_TO_REFRESH;
							changeHeaderViewByState();
						}
						// 涓�笅瀛愭帹鍒伴《浜�
						else if (tempY - mStartY <= 0) {
							mHeadState = DONE;
							changeHeaderViewByState();
						}
						// 寰�笅鎷変簡锛屾垨鑰呰繕娌℃湁涓婃帹鍒板睆骞曢《閮ㄦ帺鐩杊ead鐨勫湴姝�
					}
					// 杩樻病鏈夊埌杈炬樉绀烘澗寮�埛鏂扮殑鏃跺�,DONE鎴栬�鏄疨ULL_To_REFRESH鐘舵�
					if (mHeadState == PULL_TO_REFRESH) {

						setSelection(0);

						// 涓嬫媺鍒板彲浠ヨ繘鍏ELEASE_TO_REFRESH鐨勭姸鎬�
						if ((tempY - mStartY) / RATIO >= mHeadViewHeight) {
							mHeadState = RELEASE_TO_REFRESH;
							mIsBack = true;
							changeHeaderViewByState();
						} else if (tempY - mStartY <= 0) {
							mHeadState = DONE;
							changeHeaderViewByState();
						}
					}

					if (mHeadState == DONE) {
						if (tempY - mStartY > 0) {
							mHeadState = PULL_TO_REFRESH;
							changeHeaderViewByState();
						}
					}

					if (mHeadState == PULL_TO_REFRESH) {
						mHeadView.setPadding(0, -1 * mHeadViewHeight
								+ (tempY - mStartY) / RATIO, 0, 0);

					}

					if (mHeadState == RELEASE_TO_REFRESH) {
						mHeadView.setPadding(0, (tempY - mStartY) / RATIO
								- mHeadViewHeight, 0, 0);
					}
				}
				break;
			}
		}

		return super.onTouchEvent(event);
	}

	/**
	 * 褰揌eadView鐘舵�鏀瑰彉鏃跺�锛岃皟鐢ㄨ鏂规硶锛屼互鏇存柊鐣岄潰
	 * @date 2013-11-20 涓嬪崍4:29:44
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void changeHeaderViewByState() {
		switch (mHeadState) {
		case RELEASE_TO_REFRESH:
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
			mTipsTextView.setVisibility(View.VISIBLE);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);

			mArrowImageView.clearAnimation();
			mArrowImageView.startAnimation(mArrowAnim);
			// 鏉惧紑鍒锋柊
			mTipsTextView.setText(R.string.p2refresh_release_refresh);

			break;
		case PULL_TO_REFRESH:
			mProgressBar.setVisibility(View.GONE);
			mTipsTextView.setVisibility(View.VISIBLE);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.VISIBLE);
			// 鏄敱RELEASE_To_REFRESH鐘舵�杞彉鏉ョ殑
			if (mIsBack) {
				mIsBack = false;
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mArrowReverseAnim);
				// 涓嬫媺鍒锋柊
				mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			} else {
				// 涓嬫媺鍒锋柊
				mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			}
			break;

		case REFRESHING:
			mHeadView.setPadding(0, 0, 0, 0);
			
			// 鍗庣敓鐨勫缓璁細 瀹為檯涓婅繖涓殑setPadding鍙互鐢ㄥ姩鐢绘潵浠ｆ浛銆傛垜娌℃湁璇曪紝浣嗘槸鎴戣杩囥�鍏跺疄鏈夌殑浜轰篃鐢⊿croller鍙互瀹炵幇杩欎釜鏁堟灉锛�
			// 鎴戞病鏃堕棿鐮旂┒浜嗭紝鍚庢湡鍐嶆墿灞曪紝杩欎釜宸ヤ綔浜ょ粰灏忎紮浼翠綘浠暒~ 濡傛灉鏀硅繘浜嗚寰楀彂鍒版垜閭鍣
			// 鏈汉閭锛�xxzhaofeng5412@gmail.com
			
			mProgressBar.setVisibility(View.VISIBLE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.GONE);
			// 姝ｅ湪鍒锋柊...
			mTipsTextView.setText(R.string.p2refresh_doing_head_refresh);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);

			break;
		case DONE:
			mHeadView.setPadding(0, -1 * mHeadViewHeight, 0, 0);
			
			// 姝ゅ鍙互鏀硅繘锛屽悓涓婃墍杩般�
			
			mProgressBar.setVisibility(View.GONE);
			mArrowImageView.clearAnimation();
			mArrowImageView.setImageResource(R.drawable.arrow);
			// 涓嬫媺鍒锋柊
			mTipsTextView.setText(R.string.p2refresh_pull_to_refresh);
			mLastUpdatedTextView.setVisibility(View.VISIBLE);

			break;
		}
	}

	/**
	 * 涓嬫媺鍒锋柊鐩戝惉鎺ュ彛
	 * @date 2013-11-20 涓嬪崍4:50:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	public interface OnRefreshListener {
		public void onRefresh();
	}
	
	/**
	 * 鍔犺浇鏇村鐩戝惉鎺ュ彛
	 * @date 2013-11-20 涓嬪崍4:50:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	public interface OnLoadMoreListener {
		public void onLoadMore();
	}
	
	public void setOnRefreshListener(OnRefreshListener pRefreshListener) {
		if(pRefreshListener != null){
			mRefreshListener = pRefreshListener;
			mCanRefresh = true;
		}
	}

	public void setOnLoadListener(OnLoadMoreListener pLoadMoreListener) {
		if(pLoadMoreListener != null){
			mLoadMoreListener = pLoadMoreListener;
			mCanLoadMore = true;
			if(mCanLoadMore && getFooterViewsCount() == 0){
				addFooterView();
			}
		}
	}
	
	/**
	 * 姝ｅ湪涓嬫媺鍒锋柊
	 * @date 2013-11-20 涓嬪崍4:45:47
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void onRefresh() {
		if (mRefreshListener != null) {
			mRefreshListener.onRefresh();
		}
	}
	
	/**
	 * 涓嬫媺鍒锋柊瀹屾垚
	 * @date 2013-11-20 涓嬪崍4:44:12
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void onRefreshComplete() {
		// 涓嬫媺鍒锋柊鍚庢槸鍚︽樉绀虹涓�潯Item 
		if(mIsMoveToFirstItemAfterRefresh)setSelection(0);
		
		mHeadState = DONE;
		// 鏈�繎鏇存柊: Time
		mLastUpdatedTextView.setText(
				getResources().getString(R.string.p2refresh_refresh_lasttime) + 
				new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
		changeHeaderViewByState();
	}

	/**
	 * 姝ｅ湪鍔犺浇鏇村锛孎ootView鏄剧ず 锛�鍔犺浇涓�..
	 * @date 2013-11-20 涓嬪崍4:35:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	private void onLoadMore() {
		if (mLoadMoreListener != null) {
			// 鍔犺浇涓�..
			mEndLoadTipsTextView.setText(R.string.p2refresh_doing_end_refresh);
			mEndLoadTipsTextView.setVisibility(View.VISIBLE);
			mEndLoadProgressBar.setVisibility(View.VISIBLE);
			
			mLoadMoreListener.onLoadMore();
		}
	}

	/**
	 * 鍔犺浇鏇村瀹屾垚 
	 * @date 2013-11-11 涓嬪崍10:21:38
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void onLoadMoreComplete() {
		if(mIsAutoLoadMore){
			mEndState = ENDINT_AUTO_LOAD_DONE;
		}else{
			mEndState = ENDINT_MANUAL_LOAD_DONE;
		}
		changeEndViewByState();
	}
	
	/**
	 * 涓昏鏇存柊涓�笅鍒锋柊鏃堕棿鍟︼紒
	 * @param adapter
	 * @date 2013-11-20 涓嬪崍5:35:51
	 * @change JohnWatson
	 * @version 1.0
	 */
	public void setAdapter(BaseAdapter adapter) {
		// 鏈�繎鏇存柊: Time
		mLastUpdatedTextView.setText(getResources().getString(R.string.p2refresh_refresh_lasttime) + 
				new SimpleDateFormat(DATE_FORMAT_STR, Locale.CHINA).format(new Date()));
		super.setAdapter(adapter);
	}

}
